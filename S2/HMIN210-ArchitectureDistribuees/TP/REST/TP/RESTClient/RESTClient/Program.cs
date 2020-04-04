using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace RESTClient
{
    class Program
    {
        static HttpClient client = new HttpClient();

        static void Main(string[] args)
        {
            RunAsync().GetAwaiter().GetResult();
            Console.ReadLine();
        }

        static async Task RunAsync()
        {
            client.BaseAddress = new Uri("https://localhost:44355/api/");
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(
                new MediaTypeWithQualityHeaderValue("application/json"));

            try
            {
                Console.WriteLine("Get All rooms. \nURL: /api/rooms");
                List<Chambre> chambres = await GetAllRooms();
                for(int i=0; i < chambres.Count; i++)
                {
                    Console.WriteLine(chambres[i] + "\n===============");
                }

                Console.WriteLine("\n\n\nGetting a room by id. \nGet Request to: /api/Rooms/1");
                String r = await GetRoomById(1);
                Console.WriteLine(r + "\n==========");

                Console.WriteLine("\n\n\nGetting a room that doesn't exist. \nGet Request to: /api/rooms/9999");
                r = await GetRoomById(9999);
                Console.WriteLine(r + "\n==========");

                Console.WriteLine("\n\nSearching for rooms by its type. \nURL: /api/rooms/type/2");
                List<Chambre> rList = await SearchRoomsByType(2);
                foreach (Chambre c in rList)
                    Console.WriteLine(c.ToString() + "\n==========");

                //===========Get offers
                Console.WriteLine("\n\nGettings Offer. \nGet Request to: /api/Offer/{agence1}/{agence1}/{15-04-2020}/{20-04-2020}/{2}");
                Console.Write("Identifiant (agence1) : ");
                String identifiant = Console.ReadLine();
                Console.Write("Mot de passe (agence1): ");
                String mdp = Console.ReadLine();
                Console.Write("Saissiez la date début (15-05-2020) : ");
                string string_date_debut = Console.ReadLine();
                Console.Write("Saissiez la date fin (20-05-2020) : ");
                string string_date_fin = Console.ReadLine();
                Console.Write("Saissiez le nombre de lits (1 à 4) : ");
                int nbLits = Int32.Parse(Console.ReadLine());
                List<Offre> OffersList = await GetOffers(identifiant, mdp, string_date_debut, string_date_fin, nbLits);
                if (OffersList.Count == 0)
                {
                    Console.WriteLine("Mauvais requête. Veuillez saisir les paramètres correctes !");
                    return;
                }
                    
                foreach(Offre o in OffersList)
                {
                    Console.WriteLine(o.ToString() + "\n==========");
                }

                //============Réserver
                Console.WriteLine("\n\nMaking a reservation on Offer with id = 2. \nPost Request to: /api/Reservation/agence1/agence1/1/TRAN/My");
                Console.Write("Identifiant (agence1) : ");
                identifiant = Console.ReadLine();
                Console.Write("Mot de passe (agence1): ");
                mdp = Console.ReadLine();
                Agence a = new Agence(1, "", 0, identifiant, mdp);
                String AuthentificationRes = await Authentification(a);
                if (AuthentificationRes == "BadRequest" || AuthentificationRes == "NotFound")
                {
                    Console.WriteLine("Bad Request");
                    return;
                }
                Console.Write("Choisissez l'identifiant de l'offre réalisé : ");
                int idO = Int32.Parse(Console.ReadLine());
                Offre newOffre = await GetOffreById(idO);
                if(newOffre == null)
                {
                    Console.WriteLine("Bad Request");
                    return;
                }
                Console.Write("Nom client : ");
                String nomCl = Console.ReadLine();
                Console.Write("Prenom client : ");
                String prenomCl = Console.ReadLine();
                Client cl2 = new Client(2, nomCl, prenomCl);
                String AddClientRes = await AddClient(cl2);
                Reservation r1 = new Reservation(2, newOffre.chambreId, cl2.id, newOffre.dateEntree, newOffre.dateSortie);
                String ReservationRes = await Reserver(r1);
                Console.WriteLine(ReservationRes);
                if (ReservationRes == "Bad Request")
                    return;
                Console.WriteLine("Veuillez confirmer la réservation : 1 pour Confirmer - 0 pour Annuler ");
                String ConfirmationRes = Console.ReadLine();
                ConfirmationRes = await Confirmation(ConfirmationRes, r1, r1.id);
                Console.WriteLine(ConfirmationRes);
                
                //======Add Reservation
                /*
                Console.WriteLine("\n\nAdding a reservation on this room. \nPost Request to: /Reservations/Rooms/1");
                Client cl1 = new Client(2, "Nguyen", "Khang");
                String addClientRes = await AddClient(cl1);
                String addReservationRes = await AddReservationToRoom(1, new Reservation(c1.id, cl1.id, DateTime.Now, (DateTime.Now).AddDays(2)));
                Console.WriteLine(addReservationRes);
                */

                //=====Get All reservations
                /*
                Console.WriteLine("\n\nGetting all reservations of a room. \nGet Request to: /api/Reservations/Rooms/1");
                List<Reservation> reservations = await GetReservationOfRoom(1);
                for (int i = 0; i < reservations.Count; i++)
                    Console.WriteLine(reservations[i] + "\n=============");
                */

                //====Delete REservation
                /*
                Console.WriteLine("\n\nDeleting the reservations that we added. \nDelete Request to: /reservations/1");
                var deleteReservationRes = await DeleteReservation(1);
                Console.WriteLine(deleteReservationRes);
                */
            }
            catch(Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        //======Get all rooms
        static async Task<List<Chambre>> GetAllRooms()
        {
            List<Chambre> rooms = null;

            HttpResponseMessage reponse = await client.GetAsync("Rooms");
            if (reponse.IsSuccessStatusCode)
                rooms = await reponse.Content.ReadAsAsync<List<Chambre>>();
            return rooms;
        }

        //========Get room by Id
        static async Task<string> GetRoomById(int id)
        {
            Chambre c = null;

            HttpResponseMessage reponse = await client.GetAsync("Rooms/" + id);
            if(reponse.StatusCode == HttpStatusCode.NotFound)
            {
                return "La chambre n'existe pas !";
            }
            c = await reponse.Content.ReadAsAsync<Chambre>();

            return c.ToString();
        }

        //=========== Get Rooms by bed quantity
        static async Task<List<Chambre>> SearchRoomsByType(int bed_number)
        {
            List<Chambre> rooms = null;

            HttpResponseMessage reponse = await client.GetAsync("Rooms/bed_number=" + bed_number);
            if (reponse.IsSuccessStatusCode)
            {
                rooms = await reponse.Content.ReadAsAsync<List<Chambre>>();
            }

            return rooms;
        }

        //================Get all Reservations
        static async Task<List<Reservation>> GetReservations()
        {
            List<Reservation> reservations = null;

            HttpResponseMessage reponse = await client.GetAsync("Reservations");
            if (reponse.IsSuccessStatusCode)
                reservations = await reponse.Content.ReadAsAsync<List<Reservation>>();
            return reservations;
        }

        //===========Get all reservations of rooms by  room ID 
        static async Task<List<Reservation>> GetReservationOfRoom(int id)
        {
            List<Reservation> reservations = null;

            HttpResponseMessage response = await client.GetAsync("Reservations/Rooms/" + id);
            if (response.IsSuccessStatusCode)
                reservations = await response.Content.ReadAsAsync<List<Reservation>>();
            return reservations;
        }

        //=========== Delete Reservation by reservation id
        static async Task<string> DeleteReservation(int rid)
        {
            HttpResponseMessage response = await client.DeleteAsync("Reservation/" + rid);
            if (response.StatusCode == HttpStatusCode.NotFound)
                return "Impossible de supprimer cette réservation";
            return "La réservation a été supprimé ";
        }

        //============ Get all offers
        static async Task<List<Offre>> GetOffers(string identifiant, string mdp, string debut, string fin, int bed_number)
        {
            List<Offre> OffersList = new List<Offre>();
            HttpResponseMessage response = await client.GetAsync("Offers/" + identifiant + "/" + mdp + "/" + debut + "/" + fin + "/" + bed_number);
            //HttpResponseMessage response = await client.GetAsync("Offers/");
            if(response.StatusCode == HttpStatusCode.NotFound)
            {
                Console.WriteLine("Erreur de reception\n");
                Console.WriteLine(response.StatusCode);
            }

            if (response.IsSuccessStatusCode)
            {
                Console.WriteLine("Succès!\n");
                OffersList = await response.Content.ReadAsAsync<List<Offre>>();
            }
                
            return OffersList;
        }

        //=========Get Offer by ID
        static async Task<Offre> GetOffreById(int id)
        {
            Offre o = null;
            HttpResponseMessage response = await client.GetAsync("Offers/" + id);
            if (response.IsSuccessStatusCode)
                o = await response.Content.ReadAsAsync<Offre>();

            return o;

        }

        //=========== Reserver
        static async Task<string> Reserver(Reservation reservation)
        {
            HttpResponseMessage response = await client.PostAsJsonAsync("Reservations/", reservation);
            if (response.IsSuccessStatusCode)
            {
                Reservation ReservationRes = await response.Content.ReadAsAsync<Reservation>();
                return "La réservation a été créé. Veuillez le confirmer";
            }
            
            if(response.StatusCode == HttpStatusCode.NotFound)
            {
                return "Not Found";
            }

            return "Bad Request";
        }

        //============ Confirmation 
        static async Task<string> Confirmation(string confirmation, Reservation r, int idR)
        {
            HttpResponseMessage response;
            if (confirmation == "1")
            {
                r.confirme = true;
                response = await client.PutAsJsonAsync("Reservations/" + idR, r);
                if (response.IsSuccessStatusCode)
                {
                    Reservation res = await response.Content.ReadAsAsync<Reservation>();
                    return res.ToString();
                }
                    
                return "Not Found";
                
            }
            else
            {
                response = await client.DeleteAsync("Reservations/" + r.id);
                if (response.IsSuccessStatusCode)
                    return "La réservation a été annulé";
                return "Not Found";
                
            }
        }

        //============= Agence
        static async Task<string> Authentification(Agence agence)
        {
            HttpResponseMessage response = await client.PostAsJsonAsync("Agences/Connexion", agence);
            if (response.StatusCode == HttpStatusCode.NotFound)
                return "NotFound";

            if (response.StatusCode == HttpStatusCode.OK)
                return "Ok";

            return "BadRequest";
        }

        //============= Client
        static async Task<List<Client>> GetClients()
        {
            List<Client> clients = null;

            HttpResponseMessage reponse = await client.GetAsync("Clients");
            if (reponse.IsSuccessStatusCode)
                clients = await reponse.Content.ReadAsAsync<List<Client>>();
            return clients;
        }

        static async Task<string> AddClient(Client cl)
        {
            HttpResponseMessage response = await client.PostAsJsonAsync("Client", cl);
            if ((int)response.StatusCode == 400)
                return "Le client n'a pas pu être ajouté !";

            return "Le client ajouté à : " + response.Headers.Location;
        }

        static async Task<string> GetClientById(int id)
        {
            Client cl = null;

            HttpResponseMessage reponse = await client.GetAsync("Client/" + id);
            if (reponse.StatusCode == HttpStatusCode.NotFound)
            {
                return "Le client n'existe pas !";
            }
            cl = await reponse.Content.ReadAsAsync<Client>();

            return cl.ToString();
        }
    }
}
