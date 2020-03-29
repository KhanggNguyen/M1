using System.Web;
using System;
using System.Collections.Generic;
namespace Hotel.App_Code
{
    public class SecuredTokenWebService : System.Web.Services.Protocols.SoapHeader
    {
        public string Identifiant { get; set; }
        public string Mdp { get; set; }
        public string Token { get; set; }
        public List<Agence> agences { get; set; }

        public bool IsUserCredentialsValid(string Username, string Password)
        {
            foreach(Agence a in agences)
            {
                if (Username == a.identifiant && Password == a.motdepasse)
                    return true;
            }
            return false;
        }

        public bool IsUserCredentialsValid(SecuredTokenWebService SoapHeader)
        {
            if (SoapHeader == null)
            {
                return false;
            }

            if (!string.IsNullOrEmpty(SoapHeader.Token))
            {
                return (HttpRuntime.Cache[SoapHeader.Token] != null);
            }

            return false;
        }
    }
}