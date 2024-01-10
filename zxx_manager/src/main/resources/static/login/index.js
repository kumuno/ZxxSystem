// 表单验证
// 获取输入框
let ui = document.getElementById('boss_id')
let un = document.getElementById('boss_name')
let ue = document.getElementById('boss_phone')
let upwd = document.getElementById('boss_password')

let inun =document.getElementById('bossId-login')
let inupwd = document.getElementById('password-login')
// 获取提示文字
let bossID = document.getElementById('bossID')
let bossName = document.getElementById('bossName')
let bossPhone = document.getElementById('bossPhone')
let bossPassword =document.getElementById('bossPassword')
let inBossId = document.getElementById('inBossId')
let inBossPwd  = document.getElementById('inBossPwd')

const formValiad = {
    // 用户姓名验证  不少于6个字符
    checkBossName: function () {
        // const pattern = /$/;
        if (un.value.length === 0) {
            bossName.style.visibility = 'visible'
            bossName.innerText = '用户名不能为空'
            bossName.style.color = 'red'
            return false
        }
        // if (!pattern.test(un.value)) {
        //     bossName.style.visibility = 'visible'
        //     bossName.innerText = '用户名不合法'
        //     bossName.style.color = 'red'
        //     return false
        // }
        else {
            bossName.style.visibility = 'visible'
            bossName.innerText = '合法'
            bossName.style.color = 'green'
            return true
        }
    },

    //用户
    checkBossID:function () {
        const pattern = /^\w{6,}$/;
        if (ui.value.length === 0) {
            bossID.style.visibility = 'visible'
            bossID.innerText = '账号不能为空'
            bossID.style.color = 'red'
            return false
        }
        if (!pattern.test(ui.value)) {
            bossID.style.visibility = 'visible'
            bossID.innerText = '账号不合法'
            bossID.style.color = 'red'
            return false
        } else {
            bossID.style.visibility = 'visible'
            bossID.innerText = '合法'
            bossID.style.color = 'green'
            return true
        }
    },
    // 电话号码验证
    checkBossPhone: function () {
        const pattern = /^([0-9]{3}-?[0-9]{8}|[0-9]{4}-?[0-9]{7})$/;
        if (ue.value.length === 0) {
            bossPhone.style.visibility = 'visible'
            bossPhone.innerText = '电话号码不能为空'
            bossPhone.style.color = 'red'
            return false
        }
        if (!pattern.test(ue.value)) {
            bossPhone.style.visibility = 'visible'
            bossPhone.innerText = '电话号码不合法'
            bossPhone.style.color = 'red'
            return false
        } else {
            bossPhone.style.visibility = 'visible'
            bossPhone.innerText = '合法'
            bossPhone.style.color = 'green'
            return true
        }
    },
    // 密码验证
    checkBossPassword: function () {
        const pattern = /^\w{6,18}$/;
        if (upwd.value.length === 0) {
            bossPassword.style.visibility = 'visible'
            bossPassword.innerText = '密码不能为空'
            bossPassword.style.color = 'red'
            return false
        }
        if (!pattern.test(upwd.value)) {
            bossPassword.style.visibility = 'visible'
            bossPassword.innerText = '密码不合法'
            bossPassword.style.color = 'red'
            return false
        } else {
            bossPassword.style.visibility = 'visible'
            bossPassword.innerText = '合法'
            bossPassword.style.color = 'green'
            return true
        }
    },

    // 登录框 ID验证
    checkInBossPhone: function () {
        const pattern = /^\w{6,}$/;
        if (inun.value.length === 0) {
            inBossId.style.visibility = 'visible'
            inBossId.innerText = '账号不能为空'
            inBossId.style.color = 'red'
            return false
        }
        if (!pattern.test(inun.value)) {
            inBossId.style.visibility = 'visible'
            inBossId.innerText = '账号不合法'
            inBossId.style.color = 'red'
            return false
        } else {
            inBossId.style.visibility = 'visible'
            inBossId.innerText = '合法'
            inBossId.style.color = 'green'
            return true
        }
    },
    // 登录框 密码验证
    checkBossInPassword: function () {
        const pattern = /^\w{6,18}$/;
        if (inupwd.value.length === 0) {
            inBossPwd.style.visibility = 'visible'
            inBossPwd.innerText = '密码不能为空'
            inBossPwd.style.color = 'red'
            return false
        }
        if (!pattern.test(inupwd.value)) {
            inBossPwd.style.visibility = 'visible'
            inBossPwd.innerText = '密码不合法'
            inBossPwd.style.color = 'red'
            return false
        } else {
            inBossPwd.style.visibility = 'visible'
            inBossPwd.innerText = '合法'
            inBossPwd.style.color = 'green'
            return true
        }
    }
};