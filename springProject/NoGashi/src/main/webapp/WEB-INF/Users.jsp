<%@ page import="fr.dawan.nogashi.beans.User" %> 
<%@ page import="java.util.List" %>

<%@ include file="Header.jsp" %> 

<% 
String servletName = "userscontroler";
List<User> listUsers = (List<User>)request.getAttribute("listUsers"); 
%>
    
    <ul class="list-group">
        <% for(User u : listUsers)
           {
            %>
                <li class="list-group-item"><h3><%= u.getName() %> </h3> - <%= u.getEmail() %></li>
        <% } %>
    </ul>
    
<%@ include file="Footer.jsp" %> 