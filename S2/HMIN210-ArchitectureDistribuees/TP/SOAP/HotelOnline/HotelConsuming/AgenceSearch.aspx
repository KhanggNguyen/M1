<%@ Page Language="C#" AutoEventWireup="true" CodeFile="AgenceSearch.aspx.cs" Inherits="AgenceSearch" %>

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
        table#myTable{
            text-align:center;
        }
        table#myTable td, table#myTable th{
            width:300px;
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
    <div>
        <asp:Label ID="labelMessage" runat="server" Text="" style="color: red"></asp:Label>
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
                <td>Date Entrée </td>
                <td>
                    <asp:Calendar ID="DateEntree" runat="server"></asp:Calendar>
                </td>
            </tr>
            <tr>
                <td>Date Sortie : </td>
                <td>
                    <asp:Calendar ID="DateSortie" runat="server"></asp:Calendar>
                </td>
            </tr>
            <tr>
                <td>Nombre de personne : </td>
                <td>
                    <asp:TextBox ID="tbNombrePersonne" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <asp:Button ID="Button" runat="server" Text="Rechercher" OnClick="Button_Click"/>
                </td>
            </tr>
        </table> 
        
        <asp:Table ID="myTable" runat="server" Width="100%">
            <asp:TableRow>
                <asp:TableCell Width="10%">Identifiant de l'offre</asp:TableCell>
                <asp:TableCell Width="10%">Nombre de lits</asp:TableCell>
                <asp:TableCell Width="25%">Date Entree</asp:TableCell>
                <asp:TableCell Width="25%">Date Sortie</asp:TableCell>
                <asp:TableCell Width="5%">Prix</asp:TableCell>
                <asp:TableCell Width="20%">Image</asp:TableCell>
            </asp:TableRow>
        </asp:Table>   
        
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
