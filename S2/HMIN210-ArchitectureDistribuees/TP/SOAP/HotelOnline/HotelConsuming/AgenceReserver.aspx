<%@ Page Language="C#" AutoEventWireup="true" CodeFile="AgenceReserver.aspx.cs" Inherits="AgenceReserver" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
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
    <title></title>
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
    <div>
        <table style="font-family:Arial">
            <tr>
                <td>Identifiant : </td>
                <td>
                    <asp:TextBox ID="tbUsername" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td>Mot de passe : </td>
                <td>
                    <asp:TextBox TextMode="Password" ID="tbPassword" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td>Identifiant de l'offre : </td>
                <td>
                    <asp:TextBox ID="tbOffre" onkeypress="if(event.keyCode<48 || event.keyCode>57)event.returnValue=false;" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td>Nom de la personne réservé : </td>
                <td>
                    <asp:TextBox ID="tbNom" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td>Prénom de la réservé : </td>
                <td>
                    <asp:TextBox ID="tbPrenom" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <asp:Button ID="Button" runat="server" Text="Reserver" OnClientClick="ConfirmMessage()"  OnClick="Button_Click"/>
                    <asp:HiddenField ID="HiddenField1" runat="server" />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <asp:Label ID="lbMessageRes" runat="server"></asp:Label>
                </td>
            </tr>
        </table>
    </div>
    </form>
     <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script> 
    <script>
        function ConfirmMessage() {
            if (confirm("Voulez-vous confirmer la réservation ?")) {
                $("#HiddenField1").val("Yes");
            } else {
                $("#HiddenField1").val("No");
            }
        }
    </script>
</body>

</html>
