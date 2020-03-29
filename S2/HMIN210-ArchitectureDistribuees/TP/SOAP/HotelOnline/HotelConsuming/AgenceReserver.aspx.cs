using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;


public partial class AgenceReserver : System.Web.UI.Page
{
    AgenceServiceVatel.AgenceServiceVatel agence_service = new AgenceServiceVatel.AgenceServiceVatel();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (this.IsPostBack)
        {
            tbPassword.Attributes["value"] = tbPassword.Text;

        }
    }

    protected void Button_Click(object sender, EventArgs e)
    {
        //AgenceServiceVatel.AgenceServiceVatel agence_service = new AgenceServiceVatel.AgenceServiceVatel();
        if (Session["agence_service"] != null)
        {
            agence_service = (AgenceServiceVatel.AgenceServiceVatel)Session["agence_service"];
        }
        AgenceServiceVatel.SecuredTokenWebService securedToken = new AgenceServiceVatel.SecuredTokenWebService
        {
            Identifiant = tbUsername.Text.Trim(),
            Mdp = tbPassword.Text.Trim()
        };
        int res;
        string token = agence_service.AuthenticationUser(securedToken);
        if (token != "")
        {
            res = agence_service.Booking(Convert.ToInt32(tbOffre.Text), tbNom.Text, tbPrenom.Text);
            if (res == -1)
            {
                lbMessageRes.Text = "Aucune réservation n'a été créé car mauvais l'identifiant de l'offre";
            }
            else
            {
                string confirmValue = HiddenField1.Value;
                if (confirmValue == "Yes")
                {
                    lbMessageRes.Text = "Réservation crée et sa référence est " + res;
                }
                else
                {
                    lbMessageRes.Text = agence_service.Annulation(res);
                }
            }
        }
        else
        {
            lbMessageRes.Text = token;
        }
    }
}