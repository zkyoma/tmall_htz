<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<script>
    function checkName(){
        if($("#name").val().trim().length == 0){
            $("span.errorMessage").html("请输入账号");
            $("div.loginErrorMessageDiv").show();
            return false;
        }
        return true;
    }

    function checkPwd(){
        if($("#password").val().length == 0){
            $("span.errorMessage").html("请输入密码");
            $("div.loginErrorMessageDiv").show();
            return false;
        }
        return true;
    }

	$(function(){

        $("form.loginForm input").keyup(function(){
            $("div.loginErrorMessageDiv").hide();
        });

        $("form.loginForm").submit(function(){
            return false;
        });

		$("#subBtn").click(function () {
		    if(checkName() && checkPwd()){
		        var name = $("#name").val();
		        var pwd = $("#password").val();
				$.post("${pageContext.request.contextPath}/forelogin", {"name":name, password:pwd}, function (info) {
					if(info.flag){
						location.href = "${contextPath}/forehome";
					}else{
                        $("span.errorMessage").html(info.msg);
                        $("div.loginErrorMessageDiv").show();
                    }
				});
            }
		});

		var left = window.innerWidth/2+162;
		$("div.loginSmallDiv").css("left",left);
	});

</script>


<div id="loginDiv" style="position: relative">

	<div class="simpleLogo">
		<a href="${pageContext.request.contextPath}"><img src="img/site/simpleLogo.png"></a>
	</div>

	
	<img id="loginBackgroundImg" class="loginBackgroundImg" src="img/site/loginBackground.png">
	
	<form class="loginForm" action="#" method="post">
		<div id="loginSmallDiv" class="loginSmallDiv">
			<div class="loginErrorMessageDiv">
				<div class="alert alert-danger" >
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
				  	<span class="errorMessage"></span>
				</div>
			</div>
				
			<div class="login_acount_text">账户登录</div>
			<div class="loginInput " >
				<span class="loginInputIcon ">
					<span class=" glyphicon glyphicon-user"></span>
				</span>
				<input id="name" name="name" onblur="checkName()" placeholder="手机/会员名/邮箱" type="text">
			</div>
			
			<div class="loginInput " >
				<span class="loginInputIcon ">
					<span class=" glyphicon glyphicon-lock" ></span>
				</span>
				<input id="password" name="password" onblur="checkPwd()" type="password" placeholder="密码">
			</div>
			<span class="text-danger">不要输入真实的天猫账号密码</span><br><br>
			
			
			<div>
				<a class="notImplementLink" href="#nowhere">忘记登录密码</a> 
				<a href="register.jsp" class="pull-right">免费注册</a> 
			</div>
			<div style="margin-top:20px">
				<button class="btn btn-block redButton" id="subBtn">登录</button>
			</div>
		</div>	
	</form>


</div>	