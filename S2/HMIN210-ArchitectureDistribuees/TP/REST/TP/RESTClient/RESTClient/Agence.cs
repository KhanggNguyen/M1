using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Text;

namespace RESTClient
{
    public class Agence
    {
        public int id { get; set; }
        public string identifiant { get; set; }
        public string motdepasse { get; set; }
        public string nomAgence { get; set; }
        public double tarifPropre { get; set; }

        public Agence()
        {

        }
        
        public Agence(int idA, string n, double tp, string iden, string mdp)
        {
                id = idA;
                nomAgence = n;
                tarifPropre = tp;
                identifiant = iden;
                motdepasse = mdp;
        }

        public override string ToString()
        {
            string agence = "L'identifiant : " + id + Environment.NewLine + "Nom Agence : " + nomAgence + Environment.NewLine + "Tarif Propre : " + tarifPropre;
            StringBuilder builder = new StringBuilder();
            builder.Append(agence);

            return builder.ToString();
        }
}
}