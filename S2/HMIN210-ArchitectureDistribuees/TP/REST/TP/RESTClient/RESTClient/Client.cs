using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;


namespace RESTClient
{
    public class Client
    {
        public int id { get; set; }
        public string nom { get; set; }
        public string prenom { get; set; }

        public Client()
        {
            id = -1;
            nom = "";
            prenom = "";
        }

        public Client(int idCl, string n, string pn)
        {
           
            id = idCl;
            nom = n;
            prenom = pn;
        }

        public override string ToString()
        {
            string client = "L'identifiant : " + id + Environment.NewLine + "Nom : " + nom + Environment.NewLine + "Prenom : " + prenom;
            StringBuilder builder = new StringBuilder();
            builder.Append(client);

            return builder.ToString();

        }
    }
}