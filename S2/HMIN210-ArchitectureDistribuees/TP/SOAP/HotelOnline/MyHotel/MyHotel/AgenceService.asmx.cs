using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace MyHotel
{
    /// <summary>
    /// Summary description for AgenceService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class AgenceService : System.Web.Services.WebService
    {

        [WebMethod]
        public string CheckAvailabilityByAgencies(Agence a, DateTime date_db, DateTime date_fin, int nb_personne)
        {
            
        }
    }
}
