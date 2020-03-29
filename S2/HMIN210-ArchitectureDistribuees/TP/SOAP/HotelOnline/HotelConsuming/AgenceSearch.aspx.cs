using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class AgenceSearch : System.Web.UI.Page
{
    protected AgenceServiceVatel.AgenceServiceVatel agence_service = new AgenceServiceVatel.AgenceServiceVatel();

    protected void Application_Start(object sender, EventArgs e)
    {
        
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (this.IsPostBack)
        {
            tbPassword.Attributes["value"] = tbPassword.Text;
        }
    }

    protected void Button_Click(object sender, EventArgs e)
    {
        if (tbUsername.Text == String.Empty || tbPassword.Text == String.Empty || DateEntree.SelectedDate < DateTime.Now || DateSortie.SelectedDate < DateTime.Now || tbNombrePersonne.Text == String.Empty)
        {
            labelMessage.Text = "Les champs ne doivent pas être vide !";
            if (DateEntree.SelectedDate < DateTime.Now || DateSortie.SelectedDate < DateTime.Now || DateSortie.SelectedDate < DateEntree.SelectedDate)
                labelMessage.Text = "Veuillez saisir les dates correctements !";
        }
        else
        {
            labelMessage.Text = "";
            AgenceServiceVatel.SecuredTokenWebService securedToken = new AgenceServiceVatel.SecuredTokenWebService
            {
                Identifiant = tbUsername.Text.Trim(),
                Mdp = tbPassword.Text.Trim()
            };

            string token = agence_service.AuthenticationUser(securedToken);
            if (token != "")
            {
                char[] delimitedChars = { '-' };
                string res = agence_service.CheckAvailabilityByAgencies(DateEntree.SelectedDate, DateSortie.SelectedDate, Int32.Parse(tbNombrePersonne.Text));
                string[] res_split = res.Split(delimitedChars);
                foreach (string s in res_split)
                {
                    char[] delimitedChars2 = { ',' };
                    string[] tab_split = s.Split(delimitedChars2);
                    TableRow row = new TableRow();

                    foreach (string s2 in tab_split)
                    {   
                        if(s2.Length > 100)
                        {
                            Image img = new Image();
                            img.ImageUrl = "data:image;base64," + s2.Trim();
                        }
                        else
                        {
                            TableCell cell = new TableCell();
                            cell.Text = s2;
                            row.Cells.Add(cell);
                        }
                        
                    }
                    myTable.Rows.Add(row);
                }
                Session["agence_service"] = agence_service;
            }
            else
            {
                labelMessage.Text = token;
            }
        } 
    }
}