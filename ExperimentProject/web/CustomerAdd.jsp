<%--
  Created by IntelliJ IDEA.
  User: 54234
  Date: 2018-10-12
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加客户</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <div>
    <h1 style="text-align:center;vertical-align: center;margin:auto">添加客户</h1>
  </div>
  <div style="text-align:center;vertical-align: center;margin:auto">
    <form method='post' action='customer.do' style="text-align:center;vertical-align: center;margin:auto">
        <input type = "hidden" name = "mode" value="add">
        <table bgcolor='#cccccc' style="text-align:center;vertical-align: center;margin:auto">
            <tr>
                <td>客户ID：</td>
                <td><input type='text' name='id'></td>
            </tr>
            <tr>
                <td>客户姓名：</td>
                <td><input type='text' name='name'></td>
            </tr>
            <tr>
                <td>性别：</td>
                <td><input type="radio" name="gender" value="男">男</td><br><br>
                <td><input type="radio" name="gender" value="女">女</td>
            </tr>
            <tr>
                <td>职业：</td>
                <td><input type='text' name='job'></td>
            </tr>
            <tr>
                <td>文化程度：</td>
                <td>
                    <select name="education">
                        <option value="">请选择</option>
                        <option value="小学以下">小学以下</option>
                        <option value="小学">小学</option>
                        <option value="初中">初中</option>
                        <option value="高中">高中</option>
                        <option value="本科">本科</option>
                        <option value="硕士">硕士</option>
                        <option value="博士">博士</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>住址：</td>
                <td><input type='text' name='home'></td>
            </tr>
            

            <tr>
                <td colspan='2' align='center'><input type='submit'></td>
            </tr>
          </table>
        </form>
      <tr>
          <td colspan='2' align='center'>
              <a href="CustomerList.jsp">
                  <button>返回</button>
              </a>
          </td>
      </tr>

        </div>

</body>
</html>
