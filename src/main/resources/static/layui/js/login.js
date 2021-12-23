// layui.use(['layer'], function () {
//     var layer = layui.layer;
// })
// $(function () {
//     // 页面初始化生成验证码
//     window.onload = createCode('#loginCode');
//     // 验证码切换
//     $('#loginCode').click(function () {
//         createCode('#loginCode');
//     });
//     // 登陆事件
//     $('#loginBtn').click(function () {
//         login();
//     });
//     // 注册事件
//     $('#loginRegister').click(function () {
//         register();
//     });
// });
//
// // 生成验证码
// function createCode(codeID) {
//     var code = "";
//     // 验证码长度
//     var codeLength = 4;
//     // 验证码dom元素
//     var checkCode = $(codeID);
//     // 验证码随机数
//     var random = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
//         'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
//     for (var i = 0; i < codeLength; i++) {
//         // 随机数索引
//         var index = Math.floor(Math.random() * 36);
//         code += random[index];
//     }
//     // 将生成的随机验证码赋值
//     checkCode.val(code);
// }
//
// // 校验验证码、用户名、密码
// function validateCode(inputID, codeID) {
//     var inputCode = $(inputID).val().toUpperCase();
//     var cardCode = $(codeID).val();
//     var loginUsername = $('#loginUsername').val();
//     var loginPassword = $('#loginPassword').val();
//     if ($.trim(loginUsername) == '' || $.trim(loginUsername).length <= 0) {
//         layer.alert("用户名不能为空");
//         return false;
//     }
//     if ($.trim(loginPassword) == '' || $.trim(loginPassword).length <= 0) {
//         layer.alert("密码不能为空");
//         return false;
//     }
//     if (inputCode.length <= 0) {
//         layer.alert("验证码不能为空");
//         return false;
//     }
//     // if (inputCode != cardCode) {
//     //     layer.alert("请输入正确验证码");
//     //     return false;
//     // }
//     return true;
// }
//
// // 登录流程
// function login() {
//     if (!validateCode('#loginCard', '#loginCode')) {
//         //阻断提示
//     } else {
//         var formData = new FormData();
//         var loginUsername = $('#loginUsername').val();
//         var loginPassword = $('#loginPassword').val();
//         var params = {};
//         params.loginUsername = loginUsername;
//         params.loginPassword = loginPassword;
//         formData.append("username", loginUsername);
//         formData.append("password", loginPassword);
//         var loginLoadIndex = layer.load(2);
//         $('#loginBtn').val("正在登录...");
//         $.ajax({
//             type: 'POST',
//             // url: window.location.protocol + '//' + window.location.host + '/security-web/login',
//             url: '/login',
//             // dataType: 'html',
//             data: formData,
//             contentType: false,//这里
//             processData: false,//这两个一定设置为false
//             success: function (data) {
//                 layer.close(loginLoadIndex);
//                 var jsonData = JSON.parse(data);
//                 if (jsonData.code == '200') {
//                     $.ajax({
//                         url: '/office',
//                         headers: {
//                             'Authorization': jsonData.data;
//                 }
//                 });
//                     // window.location.href = '/office';
//                 }
//             },
//             error: function () {
//                 layer.close(loginLoadIndex);
//                 $('#loginBtn').val("登录");
//             }
//         });
//     }
//
// }
//
// // 注册流程
// function register() {
//     layer.open({
//         type: '1',
//         content: $('.registerPage'),
//         title: '注册',
//         area: ['430px', '400px'],
//         btn: ['注册', '重置', '取消'],
//         closeBtn: '1',
//         btn1: function (index, layero) {
//             //注册回调
//             layer.close(index);
//             var registerUsername = $('#registerUsername').val();
//             var registerPassword = $('#registerPassword').val();
//             var registerWellPassword = $('#registerWellPassword').val();
//             var selectValue = $('#roleSelect option:selected').val();
//             var params = {};
//             params.registerUsername = registerUsername;
//             params.registerPassword = registerPassword;
//             params.registerWellPassword = registerWellPassword;
//             params.selectValue = selectValue;
//             var registerLoadIndex = layer.load(2);
//             $.ajax({
//                 type: 'post',
//                 url: window.location.protocol + '//' + window.location.host + '/security-web/register.do',
//                 dataType: 'json',
//                 data: JSON.stringify(params),
//                 contentType: 'application/json',
//                 success: function (data) {
//                     layer.close(registerLoadIndex);
//                     layer.msg(data);
//                 },
//                 error: function () {
//                     layer.close(registerLoadIndex);
//                     layer.alert("请求超时！")
//                 }
//             });
//         },
//         btn2: function (index, layero) {
//             //重置回调
//             var registerUsername = $('#registerUsername').val("");
//             var registerPassword = $('#registerPassword').val("");
//             var registerWellPassword = $('#registerWellPassword').val("");
//             // 防止注册页面关闭
//             return false;
//         },
//         btn3: function (index, layero) {
//             //取消回调
//         }
//     })
// }