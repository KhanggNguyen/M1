<%@ Page Language="C#" AutoEventWireup="true" CodeFile="index.aspx.cs" Inherits="index" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>

    <style type="text/css">
        body{
            width:100%
        }
        ul {
            height: 50px;
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            background-color: #333;
        }

        li {
            float: left;
        }

        li a {
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }

        li a:hover {
            background-color: yellow;
            color:black
        }

        table {
            display:block;
        }
    </style>

</head>
<body>
    <form id="form1" runat="server">
    <div style="height: 100%">
        <ul>
            <li><a class="active" href="Index.aspx">Accueil</a></li>
            <li><a href="AgenceSearch.aspx">Recherche Agence</a></li>
            <li><a href="AgenceReserver.aspx">Agence Reserver</a></li>
        </ul>
    </div>
    </form>
</body>
</html>
