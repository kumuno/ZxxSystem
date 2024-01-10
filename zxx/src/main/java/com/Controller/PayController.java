package com.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.Config;
import com.common.RandomStringGenerator;
import com.common.ResultInfo;
import com.common.StreamUtil;
import com.wechat.pay.contrib.apache.httpclient.auth.*;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wechat.pay.contrib.apache.httpclient.util.RsaCryptoUtil;
import com.common.*;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;

@Controller
@RequestMapping("")
public class PayController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 支付
	 * @param totalFee
	 * @param code
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * 视频教程参考:https://space.bilibili.com/431152063
	 * 密云榛子IT教育 http://it.zhenzikj.com
	 */
	@RequestMapping("/pay")
	@ResponseBody
	public ResultInfo<Object> pay(double totalFee, String code, String listGoodName) throws ParseException, IOException{
		try {
			JSONObject order = new JSONObject();
			order.put("appid", Config.getWeixinMiniAppid());
			order.put("mchid", Config.getWeixinMiniMchId());
			order.put("description", listGoodName);
			order.put("out_trade_no", new DateTime().toString("yyyyMMddHHmmssSSS"));
			order.put("notify_url", Config.getWeixinMiniNotifyUrl());
			
			JSONObject amount = new JSONObject();
			amount.put("total", (long)(totalFee * 100));
			amount.put("currency", "CNY");
			order.put("amount", amount);
			
			JSONObject payer = new JSONObject();
			payer.put("openid", this.getOpenid(code));
			order.put("payer", payer);

			logger.info(order.toJSONString());

			PrivateKey merchantPrivateKey = getPrivateKey();

            // 使用定时更新的签名验证器，不需要传入证书
			ScheduledUpdateCertificatesVerifier verifier = new ScheduledUpdateCertificatesVerifier(
					new WechatPay2Credentials(Config.getWeixinMiniMchId(), new PrivateKeySigner(Config.getWeixinMiniMerchantSerialNumber(), merchantPrivateKey)),
					Config.getWeixinMiniApiV3Key().getBytes(StandardCharsets.UTF_8));
			WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
			        .withMerchant(Config.getWeixinMiniMchId(), Config.getWeixinMiniMerchantSerialNumber(), merchantPrivateKey)
			        .withValidator(new WechatPay2Validator(verifier));
			// ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient

			// 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
			HttpClient httpClient = builder.build();
			HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi");
			httpPost.addHeader("Accept", "application/json");
			httpPost.addHeader("Content-type","application/json; charset=utf-8");
			httpPost.setEntity(new StringEntity(order.toJSONString(), "UTF-8"));
			// 后面跟使用Apache HttpClient一样
			HttpResponse response = httpClient.execute(httpPost);
			String bodyAsString = EntityUtils.toString(response.getEntity());
			
			JSONObject bodyAsJSON = JSONObject.parseObject(bodyAsString);
			logger.info(bodyAsJSON.toJSONString());
			if(bodyAsJSON.containsKey("code")) {
				return new ResultInfo(1, bodyAsJSON.getString("message"));
			}
			final String prepay_id = bodyAsJSON.getString("prepay_id");
			final String timeStamp = String.valueOf(System.currentTimeMillis());
			final String nonceStr = RandomStringGenerator.getRandomStringByLength(32);
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(Config.getWeixinMiniAppid() + "\n");
			stringBuffer.append(timeStamp + "\n");
			stringBuffer.append(nonceStr + "\n");
			stringBuffer.append("prepay_id="+prepay_id+"\n");
			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initSign(merchantPrivateKey);
			signature.update(stringBuffer.toString().getBytes("UTF-8"));
			byte[] signBytes = signature.sign();
			String paySign = Base64.encodeBytes(signBytes);
			
			JSONObject params = new JSONObject();
			params.put("appId", Config.getWeixinMiniAppid());
			params.put("timeStamp", timeStamp);
			params.put("nonceStr", nonceStr);
			params.put("prepay_id", prepay_id);
			params.put("signType", "RSA");
//			params.put("signType", "JSAPI");
			params.put("paySign", paySign);
			
			return new ResultInfo<Object>(0, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
			e.printStackTrace();
		}
		return null;
		
	}

	/**
	 * 获取API私钥
	 * @return
	 */
	private PrivateKey getPrivateKey(){
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("apiclient_key.pem");
		PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(inputStream);
        return merchantPrivateKey;
	}
	
	private String getOpenid(String code) throws ParseException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+Config.getWeixinMiniAppid()+"&secret="+Config.getWeixinMiniSecret()+"&js_code="+code+"&grant_type=authorization_code";
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(get);
		String result = EntityUtils.toString(response.getEntity(), "utf-8");
		JSONObject json = JSONObject.parseObject(result);
		if(!json.containsKey("errcode")) {
			String openid = json.getString("openid");
			return openid;
		}
		return "";
	}
	/**
	 * 支付通知(api)
	 */
	@RequestMapping(value="/payNotice")
	public void payNotice(
			HttpServletRequest request, 
			HttpServletResponse response){
		try {
			String reqParams = StreamUtil.read(request.getInputStream());
			logger.info("-------支付结果:"+reqParams);
			JSONObject json = JSONObject.parseObject(reqParams);
            if(json.getString("event_type").equals("TRANSACTION.SUCCESS")){
				logger.info("-------支付成功");

			}
			String ciphertext = json.getJSONObject("resource").getString("ciphertext");
			final String associated_data = json.getJSONObject("resource").getString("associated_data");
			final String nonce = json.getJSONObject("resource").getString("nonce");
			AesUtil aesUtil = new AesUtil(Config.getWeixinMiniApiV3Key().getBytes());
			ciphertext = aesUtil.decryptToString(associated_data.getBytes(), nonce.getBytes(), ciphertext);
			logger.info("-------ciphertext:"+ciphertext);
			logger.info(JSONObject.parseObject(ciphertext).getString("out_trade_no"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
	}

	/**
	 * 退款(api)
	 */
	@RequestMapping(value="/refund")
	public void refund(
			String out_trade_no,
			String out_refund_no,
			String reason,
			double totalFee,
			double total
			){
		try {
			JSONObject order = new JSONObject();
			order.put("out_trade_no", out_trade_no);//商户订单号
			order.put("out_refund_no", out_refund_no);//商户退款单号
			order.put("reason", reason);//退款原因
			order.put("notify_url", Config.getWeixinMiniRefundNotifyUrl());//退款通知

			JSONObject amount = new JSONObject();
			amount.put("refund", (long)(totalFee * 100));//退款金额
			amount.put("currency", "CNY");
			amount.put("total", (long)(total * 100));//原订单金额
			order.put("amount", amount);

			PrivateKey merchantPrivateKey = getPrivateKey();

			// 使用定时更新的签名验证器，不需要传入证书
			ScheduledUpdateCertificatesVerifier verifier = new ScheduledUpdateCertificatesVerifier(
					new WechatPay2Credentials(Config.getWeixinMiniMchId(), new PrivateKeySigner(Config.getWeixinMiniMerchantSerialNumber(), merchantPrivateKey)),
					Config.getWeixinMiniApiV3Key().getBytes(StandardCharsets.UTF_8));
			WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
					.withMerchant(Config.getWeixinMiniMchId(), Config.getWeixinMiniMerchantSerialNumber(), merchantPrivateKey)
					.withValidator(new WechatPay2Validator(verifier));
			// ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient

			// 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
			HttpClient httpClient = builder.build();
			HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/refund/domestic/refunds");
			httpPost.addHeader("Accept", "application/json");
			httpPost.addHeader("Content-type","application/json; charset=utf-8");
			httpPost.setEntity(new StringEntity(order.toJSONString(), "UTF-8"));
			// 后面跟使用Apache HttpClient一样
			HttpResponse response = httpClient.execute(httpPost);
			String bodyAsString = EntityUtils.toString(response.getEntity());

			JSONObject bodyAsJSON = JSONObject.parseObject(bodyAsString);
			logger.info(bodyAsJSON.toJSONString());

			final String status = bodyAsJSON.getString("status");
			if(status.equals("SUCCESS")){
				logger.info("退款成功");
			}else if(status.equals("CLOSED")){
				logger.info("退款关闭");
			}else if(status.equals("PROCESSING")){
				logger.info("退款处理中");
			}else if(status.equals("ABNORMAL")){
				logger.info("退款异常");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
			e.printStackTrace();
		}
	}
	/**
	 * 退款通知(api)
	 */
	@RequestMapping(value="/refundNotice")
	public void refundNotice(
			HttpServletRequest request,
			HttpServletResponse response){
		try {
			String reqParams = StreamUtil.read(request.getInputStream());
			logger.info("-------支付结果:"+reqParams);
			JSONObject json = JSONObject.parseObject(reqParams);
			final String event_type = json.getString("event_type");
			if(event_type.equals("REFUND.SUCCESS")){
				logger.info("-------退款成功");
			}else if(event_type.equals("REFUND.ABNORMAL")){
				logger.info("-------退款异常");
			}else if(event_type.equals("REFUND.CLOSED")){
				logger.info("-------退款关闭");
			}
			String ciphertext = json.getJSONObject("resource").getString("ciphertext");
			final String associated_data = json.getJSONObject("resource").getString("associated_data");
			final String nonce = json.getJSONObject("resource").getString("nonce");
			AesUtil aesUtil = new AesUtil(Config.getWeixinMiniApiV3Key().getBytes());
			ciphertext = aesUtil.decryptToString(associated_data.getBytes(), nonce.getBytes(), ciphertext);
			logger.info("-------ciphertext:"+ciphertext);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
	}
}
