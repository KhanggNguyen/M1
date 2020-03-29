using System;
using System.Collections.Generic;
using System.Linq;
using System.Data.SqlClient;
using System.Configuration;
using System.Web;
using System.Web.Services;
using System.IO;

/// <summary>
/// Summary description for WebService
/// </summary>
[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
// [System.Web.Script.Services.ScriptService]
public class AgenceServiceVatel : System.Web.Services.WebService
{
    public static string paren_directory = Path.GetFullPath(Path.Combine(AppDomain.CurrentDomain.BaseDirectory));
    public Hotel.App_Code.SecuredTokenWebService SoapHeader;
    public List<Hotel.App_Code.Agence> agences = new List<Hotel.App_Code.Agence>();
    public Hotel.App_Code.Hotel hotel;
    public List<Hotel.App_Code.Offre> Offres = new List<Hotel.App_Code.Offre>();
    public List<Hotel.App_Code.Reservation> Reservations = new List<Hotel.App_Code.Reservation>();

    //Initialiser les Hotels
    public void initializeHotel()
    {
        Hotel.App_Code.Adresse a1 = new Hotel.App_Code.Adresse("France", "Paris", "Rue Nollet, 75017 Paris", "122", "Ecole Hôtelière Vatel Paris", "voici la position GPS");
        Hotel.App_Code.Hotel vatel = new Hotel.App_Code.Hotel("Vatel", a1, 4);
        Hotel.App_Code.Chambre vC1 = new Hotel.App_Code.Chambre(1, 100, vatel, paren_directory + "/Images/vC1.jpg");
        Hotel.App_Code.Chambre vC2 = new Hotel.App_Code.Chambre(2, 200, vatel, paren_directory + "/Images/vC2.jpg");
        Hotel.App_Code.Chambre vC6 = new Hotel.App_Code.Chambre(2, 200, vatel, paren_directory + "/Images/vC2.jpg");
        Hotel.App_Code.Chambre vC3 = new Hotel.App_Code.Chambre(3, 300, vatel, paren_directory + "/Images/vC3.jpg");
        Hotel.App_Code.Chambre vC4 = new Hotel.App_Code.Chambre(4, 400, vatel, paren_directory + "/Images/vC4.jpg");
        Hotel.App_Code.Chambre vC5 = new Hotel.App_Code.Chambre(5, 500, vatel, paren_directory + "/Images/vC5.jpg");
        
        vatel.addChambre(vC1);
        vatel.addChambre(vC2);
        vatel.addChambre(vC3);
        vatel.addChambre(vC4);
        vatel.addChambre(vC5);
        vatel.addChambre(vC6);
        hotel = vatel;

        Hotel.App_Code.Offre o = new Hotel.App_Code.Offre(vC1, DateTime.Now, (DateTime.Now).AddDays(2), 2);
        Hotel.App_Code.Offre o2 = new Hotel.App_Code.Offre(vC2, DateTime.Now, (DateTime.Now).AddDays(2), 2);
        Hotel.App_Code.Offre o3 = new Hotel.App_Code.Offre(vC3, DateTime.Now, (DateTime.Now).AddDays(2), 2);
        Hotel.App_Code.Offre o4 = new Hotel.App_Code.Offre(vC4, DateTime.Now, (DateTime.Now).AddDays(2), 2);
        Hotel.App_Code.Offre o5 = new Hotel.App_Code.Offre(vC5, DateTime.Now, (DateTime.Now).AddDays(2), 2);

        Offres.Add(o);
        Offres.Add(o2);
        Offres.Add(o3);
        Offres.Add(o4);
        Offres.Add(o5);
    }

    public void initializeAgence()
    {
        Hotel.App_Code.Agence admin = new Hotel.App_Code.Agence("admin", 1.05, "admin", "admin");
        Hotel.App_Code.Agence a1 = new Hotel.App_Code.Agence("agence1", 1.10, "agence1", "agence1");

        agences.Add(admin);
        agences.Add(a1);
    }

    public void initializeOffres()
    {
        
    }

    public AgenceServiceVatel()
    {

        //Uncomment the following line if using designed components 
        //InitializeComponent(); 
        initializeHotel();
        initializeAgence();
    }

    public byte[] StreamToByteArray(string fileName)
    {
        byte[] total_stream = new byte[0];
        using (Stream input = File.Open(fileName, FileMode.Open, FileAccess.Read))
        {
            byte[] stream_array = new byte[0];
            // Setup whatever read size you want (small here for testing)
            byte[] buffer = new byte[32];// * 1024];
            int read = 0;

            while ((read = input.Read(buffer, 0, buffer.Length)) > 0)
            {
                stream_array = new byte[total_stream.Length + read];
                total_stream.CopyTo(stream_array, 0);
                Array.Copy(buffer, 0, stream_array, total_stream.Length, read);
                total_stream = stream_array;
            }
        }
        return total_stream;
    }

    public string buildStringRes()
    {
        string res = "";
        foreach (Hotel.App_Code.Offre o in Offres)
        {
            res += o.ID + " , " + o.chambre.nombreLits + " , " + o.dateEntree.ToString() + " , " + o.dateSortie.ToString() + " , " + (o.prix).ToString() + " , " + Convert.ToBase64String(this.StreamToByteArray(o.chambre.file_name)); ;
            res += " - ";
        }
        return res;
    }

    public string getOffres(DateTime date_db, DateTime date_fin, int nb_personne)
    {   
        foreach(Hotel.App_Code.Chambre c in hotel.mesChambres)
        {
            if(nb_personne <= c.nombreLits)
            {
                bool reserve = false;
                if(Reservations.Count > 0)
                {
                    foreach (Hotel.App_Code.Reservation r in Reservations)
                    {
                        if (r.chambre == c && date_fin <= r.dateSortie && date_db >= r.dateEntree)
                        {
                            reserve = true;
                        }
                    }
                }
                
                if(reserve == false)
                {
                    Hotel.App_Code.Offre o = new Hotel.App_Code.Offre(c, date_db, date_fin, ((date_fin - date_db).TotalDays));
                    Offres.Add(o);
                }
            }
        }
        return buildStringRes();
    }

    [WebMethod]
    [System.Web.Services.Protocols.SoapHeader("SoapHeader")]
    public string AuthenticationUser(Hotel.App_Code.SecuredTokenWebService SoapHeader) 
    {
        
        if (SoapHeader == null)
            return "L'identifiant ou Mdp ne doit pas être vide";
        if (string.IsNullOrEmpty(SoapHeader.Identifiant) || string.IsNullOrEmpty(SoapHeader.Mdp))
            return "L'identifiant ou Mdp ne doit pas être vide";

        if (SoapHeader != null)
        {
            SoapHeader.agences = agences;
        }
        if (!SoapHeader.IsUserCredentialsValid(SoapHeader.Identifiant, SoapHeader.Mdp))
            return "Mauvais l'identifiant ou mdp";

        string token = Guid.NewGuid().ToString();
        HttpRuntime.Cache.Add(
            token,
            SoapHeader.Identifiant,
            null,
            System.Web.Caching.Cache.NoAbsoluteExpiration,
            TimeSpan.FromMinutes(30),
            System.Web.Caching.CacheItemPriority.NotRemovable,
            null
            );
        return token;
    }

    [WebMethod]
    public string CheckAvailabilityByAgencies(DateTime date_db, DateTime date_fin, int nb_personne)
    {
        return getOffres(date_db, date_fin, nb_personne);
    }

    [WebMethod]
    public int Booking(int idOffre, string nom, string prenom)
    {
        int counter = 0;
        Hotel.App_Code.Client cl = new Hotel.App_Code.Client(nom, prenom);
        
        Hotel.App_Code.Offre o = null;
        
        foreach(Hotel.App_Code.Offre o_tempo in Offres)
        {
            o = o_tempo;
        }
        Hotel.App_Code.Reservation r;
        if (o != null)
        {
            r = new Hotel.App_Code.Reservation(o.chambre, cl, o.dateEntree, o.dateSortie);
            Reservations.Add(r);
            return r.ID;
        }
        return counter;
    }

    [WebMethod]
    public string Annulation(int idReservation)
    {
        Hotel.App_Code.Reservation r = Reservations.Find(Reservation => Reservation.ID == idReservation);
        Reservations.Remove(r);
        return "La réservation est annulé";
    }

}
