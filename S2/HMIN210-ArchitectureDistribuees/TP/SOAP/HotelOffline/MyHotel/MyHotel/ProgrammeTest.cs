using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MyHotel
{
    public class ProgrammeTest
    {
        static void Main(string[] args)
        {
            //Hotel
            Adresse a1 = new Adresse("France", "Paris", "Rue Nollet, 75017 Paris", "122", "Ecole Hôtelière Vatel Paris", "voici la position GPS");
            Hotel vatel = new Hotel("Vatel", a1, 4);
            Chambre vC1 = new Chambre(2, 200, vatel);
            Chambre vC2 = new Chambre(2, 200, vatel);
            vatel.addChambre(vC1);
            vatel.addChambre(vC2);

            Adresse a2 = new Adresse("France", "Paris", "Rue de Libourne, 75012 Paris", "1", "Pullman Paris Bercy", "voici la position GPS");
            Hotel pullman = new Hotel("Pullman", a2, 5);
            Chambre pC1 = new Chambre(2, 250, pullman);
            Chambre pC2 = new Chambre(2, 250, pullman);
            pullman.addChambre(pC1);
            pullman.addChambre(pC2);

            //Client
            Client khang = new Client("Nguyen", "Huu Khang");
            CB cbKhang = new CB("123 456 789 012", "1234");
            khang.addCB(cbKhang);

            Agence ag = new Agence("Kayak", 0.1, "admin", "admin");
            List<Hotel> lHotel = ag.getHotels();

            //Reservation
            var dt_debut = new DateTime(2020, 03, 17, 12, 0, 0, DateTimeKind.Utc);
            string dt_debut_string = dt_debut.ToLocalTime().ToString("yyyy-MM-dd HH:mm:ss \"GMT\"zzz");
            var dt_fin = new DateTime(2020, 03, 19, 12, 0, 0, DateTimeKind.Utc);
            string dt_fin_string = dt_debut.ToLocalTime().ToString("yyyy-MM-dd HH:mm:ss \"GMT\"zzz");
            Reservation r_khang_vatel = new Reservation(vC1, khang, dt_debut, dt_fin);

        }
    }
}