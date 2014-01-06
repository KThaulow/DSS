/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mediator;

import entities.Aircraft;
import java.util.HashMap;

/**
 *
 * @author Henrik
 */
public class AircraftManager {
    
    private HashMap<String, Aircraft> aircrafts;
    
    private AircraftManager() {
        aircrafts = new HashMap<>();
        aircrafts.put("KaspersFly", new Aircraft("KaspersFly", 1, 162, 450, 2400));
        aircrafts.put("PetersFly", new Aircraft("PetersFly", 2, 70, 250, 550));
        aircrafts.put("KristiansFly", new Aircraft("KristiansFly", 3, 148, 450, 2300));
        aircrafts.put("HenriksFly", new Aircraft("HenriksFly", 4, 265, 450, 4700));
        aircrafts.put("KaspegfdgdhhhrsFly1", new Aircraft("KaspersFly1", 5, 72, 440, 1700));
        aircrafts.put("PeterssagsaasaaaaFly1", new Aircraft("PetersFly1", 6, 162, 450, 2400));
        aircrafts.put("KrissafgasfhtiansFly1", new Aircraft("KristiansFly1", 7, 70, 250, 550));
        aircrafts.put("HenriksFegwasrly1", new Aircraft("HenriksFly1", 8, 148, 450, 2300));
        aircrafts.put("KaspsadgsaersFly2", new Aircraft("KaspersFly", 9, 265, 450, 4700));
        aircrafts.put("PetnbdfbnnnersFly2", new Aircraft("PetersFly", 10, 72, 440, 1700));
        aircrafts.put("adfKaspersFly", new Aircraft("KaspersFly", 11, 162, 450, 2400));
        aircrafts.put("PetsadfaeeersFly", new Aircraft("PetersFly", 12, 70, 250, 550));
        aircrafts.put("KristiansFgfafaly", new Aircraft("KristiansFly", 13, 148, 450, 2300));
        aircrafts.put("HesadfsdaffnriksFly", new Aircraft("HenriksFly", 14, 265, 450, 4700));
        aircrafts.put("KaspersagsagnsFly1", new Aircraft("KaspersFly1", 15, 72, 440, 1700));
        aircrafts.put("PetsgaersasdFly1", new Aircraft("PetersFly1", 16, 162, 450, 2400));
        aircrafts.put("KristiansadfsafsFly1", new Aircraft("KristiansFly1", 17, 70, 250, 550));
        aircrafts.put("HenasdriksFsfagafsgly1", new Aircraft("HenriksFly1", 18, 148, 450, 2300));
        aircrafts.put("KaspersFgasgfdgly2", new Aircraft("KaspersFly", 19, 265, 450, 4700));
        aircrafts.put("PedafgargtersFly2", new Aircraft("PetersFly", 20, 72, 440, 1700));
        aircrafts.put("KaspersersdfgFly", new Aircraft("KaspersFly", 21, 162, 450, 2400));
        aircrafts.put("PetersFlfdgray", new Aircraft("PetersFly", 22, 70, 250, 550));
        aircrafts.put("Kagsa4rristiansFly", new Aircraft("KristiansFly", 23, 148, 450, 2300));
        aircrafts.put("HenrikgasgrsFly", new Aircraft("HenriksFly", 24, 265, 450, 4700));
        aircrafts.put("Kaspersadf4sFly1", new Aircraft("KaspersFly1", 25, 72, 440, 1700));
        aircrafts.put("PetdasfdsafersFly1", new Aircraft("PetersFly1", 26, 162, 450, 2400));
        aircrafts.put("KristianssafdaeFly1", new Aircraft("KristiansFly1", 27, 70, 250, 550));
        aircrafts.put("HenrikasfdsFly1", new Aircraft("HenriksFly1", 28, 148, 450, 2300));
        aircrafts.put("Kashststrh55persFly2", new Aircraft("KaspersFly", 29, 265, 450, 4700));
        aircrafts.put("Petersthr55rsFly2", new Aircraft("PetersFly", 30, 72, 440, 1700));
        aircrafts.put("Kaspersshh5rusr5jFly", new Aircraft("KaspersFly", 31, 162, 450, 2400));
        aircrafts.put("PedsfgdshtersFly", new Aircraft("PetersFly", 32, 70, 250, 550));
        aircrafts.put("KristgrdgdsfgiansFly", new Aircraft("KristiansFly", 33, 148, 450, 2300));
        aircrafts.put("HenriksFthrrt6888ly", new Aircraft("HenriksFly", 34, 265, 450, 4700));
        aircrafts.put("Kasp54u5454u7788ersFly1", new Aircraft("KaspersFly1", 35, 72, 440, 1700));
        aircrafts.put("Peters432y43i76oFly1", new Aircraft("PetersFly1", 36, 162, 450, 2400));
        aircrafts.put("Kristians4y45e4u46jFly1", new Aircraft("KristiansFly1", 37, 70, 250, 550));
        aircrafts.put("Henrseh4e32iksFly1", new Aircraft("HenriksFly1", 38, 148, 450, 2300));
        aircrafts.put("Kasperey544565uisFly2", new Aircraft("KaspersFly", 39, 265, 450, 4700));
        aircrafts.put("Petehsetrtsh444rsFly2", new Aircraft("PetersFly", 40, 72, 440, 1700));
        aircrafts.put("Kasperhtsrh557772sFly", new Aircraft("KaspersFly", 41, 162, 450, 2400));
        aircrafts.put("Petererge45sFly", new Aircraft("PetersFly", 42, 70, 250, 550));
        aircrafts.put("Kris43t43y2tiansFly", new Aircraft("KristiansFly", 43, 148, 450, 2300));
        aircrafts.put("Henrike4sg4e43sFly", new Aircraft("HenriksFly", 44, 265, 450, 4700));
        aircrafts.put("KaspersFgsergrse44ly1", new Aircraft("KaspersFly1", 45, 72, 440, 1700));
        aircrafts.put("Petesrdg44eyh577888rsFly1", new Aircraft("PetersFly1", 46, 162, 450, 2400));
        aircrafts.put("Kristiane44egesgsesFly1", new Aircraft("KristiansFly1", 47, 70, 250, 550));
        aircrafts.put("Heegrer4rnriksFly1", new Aircraft("HenriksFly1", 48, 148, 450, 2300));
        aircrafts.put("KaspersF44etagesrly2", new Aircraft("KaspersFly", 49, 265, 450, 4700));
        aircrafts.put("PetersFdsfhsdfh4ely2", new Aircraft("PetersFly", 50, 72, 440, 1700));
        aircrafts.put("KaspersFdsfhdsfhly", new Aircraft("KaspersFly", 51, 162, 450, 2400));
        aircrafts.put("PetjryndtyjmnersFly", new Aircraft("PetersFly", 52, 70, 250, 550));
        aircrafts.put("KristiaawfagnsFly", new Aircraft("KristiansFly", 53, 148, 450, 2300));
        aircrafts.put("Henre5sgerhrtiksFly", new Aircraft("HenriksFly", 54, 265, 450, 4700));
        aircrafts.put("Kaspeshse55ersFly1", new Aircraft("KaspersFly1", 55, 72, 440, 1700));
        aircrafts.put("PetersFhserethly1", new Aircraft("PetersFly1", 56, 162, 450, 2400));
        aircrafts.put("KristiansehsresrFly1", new Aircraft("KristiansFly1", 57, 70, 250, 550));
        aircrafts.put("Henriks5ehe5Fly1", new Aircraft("HenriksFly1", 58, 148, 450, 2300));
        aircrafts.put("KaspersFeshtresly2", new Aircraft("KaspersFly", 59, 265, 450, 4700));
        aircrafts.put("PetdshghersFly2", new Aircraft("PetersFly", 60, 72, 440, 1700));
        aircrafts.put("hsehKaspersFly", new Aircraft("KaspersFly", 61, 162, 450, 2400));
        aircrafts.put("PetersF4geegly", new Aircraft("PetersFly", 62, 70, 250, 550));
        aircrafts.put("KrwefwefistiansFly", new Aircraft("KristiansFly", 63, 148, 450, 2300));
        aircrafts.put("HwefweenriksFly", new Aircraft("HenriksFly", 64, 265, 450, 4700));
        aircrafts.put("Kaspers4r5y6u8iFly1", new Aircraft("KaspersFly1", 65, 72, 440, 1700));
        aircrafts.put("Peter4r5t6yyu7sFly1", new Aircraft("PetersFly1", 66, 162, 450, 2400));
        aircrafts.put("KristiansFlr4gre5y1", new Aircraft("KristiansFly1", 67, 70, 250, 550));
        aircrafts.put("Henrg423wrty6iksFly1", new Aircraft("HenriksFly1", 68, 148, 450, 2300));
        aircrafts.put("KaspersFlat4r2y2", new Aircraft("KaspersFly", 69, 265, 450, 4700));
        aircrafts.put("Peteasgd43rsFly2", new Aircraft("PetersFly", 70, 72, 440, 1700));
        aircrafts.put("K34ataspersFly", new Aircraft("KaspersFly", 71, 162, 450, 2400));
        aircrafts.put("PeterssdgfFly", new Aircraft("PetersFly", 72, 70, 250, 550));
        aircrafts.put("KrisdfgstiansFly", new Aircraft("KristiansFly", 73, 148, 450, 2300));
        aircrafts.put("HenriksFlyfsjfgjghs", new Aircraft("HenriksFly", 74, 265, 450, 4700));
        aircrafts.put("KaspersFlyjfgsfj1", new Aircraft("KaspersFly1", 75, 72, 440, 1700));
        aircrafts.put("PetershrttrhsrsFly1", new Aircraft("PetersFly1", 76, 162, 450, 2400));
        aircrafts.put("KristianssthrstrFly1", new Aircraft("KristiansFly1", 77, 70, 250, 550));
        aircrafts.put("HenriksFlysthtr1", new Aircraft("HenriksFly1", 78, 148, 450, 2300));
        aircrafts.put("KaspersFsbdfyly2", new Aircraft("KaspersFly", 79, 265, 450, 4700));
        aircrafts.put("PedbsbbsdtersFly2", new Aircraft("PetersFly", 80, 72, 440, 1700));
        aircrafts.put("KaspersFlhdsbdsy", new Aircraft("KaspersFly", 81, 162, 450, 2400));
        aircrafts.put("PetgrseesrersFly", new Aircraft("PetersFly", 82, 70, 250, 550));
        aircrafts.put("KristiansFatraertly", new Aircraft("KristiansFly", 83, 148, 450, 2300));
        aircrafts.put("HenriksjfgseaFly", new Aircraft("HenriksFly", 84, 265, 450, 4700));
        aircrafts.put("KaspersFghfmnvly1", new Aircraft("KaspersFly1", 85, 72, 440, 1700));
        aircrafts.put("PetersFlybnmbm1", new Aircraft("PetersFly1", 86, 162, 450, 2400));
        aircrafts.put("KrisfyjtfjhgtiansFly1", new Aircraft("KristiansFly1", 87, 70, 250, 550));
        aircrafts.put("HenriksFkyuyukly1", new Aircraft("HenriksFly1", 88, 148, 450, 2300));
        aircrafts.put("KaspersFlkuhguyy2", new Aircraft("KaspersFly", 89, 265, 450, 4700));
        aircrafts.put("PeteyukgyukrsFly2", new Aircraft("PetersFly", 90, 72, 440, 1700));
        aircrafts.put("KaspersFlguiæly", new Aircraft("KaspersFly", 91, 162, 450, 2400));
        aircrafts.put("PeterghkhsFly", new Aircraft("PetersFly", 92, 70, 250, 550));
        aircrafts.put("KristiansFgerewrgly", new Aircraft("KristiansFly", 93, 148, 450, 2300));
        aircrafts.put("HenrerwgerwgiksFly", new Aircraft("HenriksFly", 94, 265, 450, 4700));
        aircrafts.put("KasperegewrgsFly1", new Aircraft("KaspersFly1", 95, 72, 440, 1700));
        aircrafts.put("PeterserwgerwgFly1", new Aircraft("PetersFly1", 96, 162, 450, 2400));
        aircrafts.put("KristianewrhjtrsFly1", new Aircraft("KristiansFly1", 97, 70, 250, 550));
        aircrafts.put("HenriksgewFly1", new Aircraft("HenriksFly1", 98, 148, 450, 2300));
        aircrafts.put("Kas25t2egpersFly2", new Aircraft("KaspersFly", 99, 265, 450, 4700));
        aircrafts.put("PetersFlerwgerwgy2", new Aircraft("PetersFly", 100, 72, 440, 1700));
        aircrafts.put("KasperseregrggFly", new Aircraft("KaspersFly", 101, 162, 450, 2400));
        aircrafts.put("Petersewrg3Fly", new Aircraft("PetersFly", 102, 70, 250, 550));
        aircrafts.put("Kristians343gwFly", new Aircraft("KristiansFly", 103, 148, 450, 2300));
        aircrafts.put("Henrert43wiksFly", new Aircraft("HenriksFly", 104, 265, 450, 4700));
        aircrafts.put("KaspersFlerwgweryy1", new Aircraft("KaspersFly1", 105, 72, 440, 1700));
        aircrafts.put("PetergewrgersFly1", new Aircraft("PetersFly1", 106, 162, 450, 2400));
        aircrafts.put("KristiansFregrly1", new Aircraft("KristiansFly1", 107, 70, 250, 550));
        aircrafts.put("HenriksFlhgfy1", new Aircraft("HenriksFly1", 108, 148, 450, 2300));
        aircrafts.put("KaspersFliuiuy2", new Aircraft("KaspersFly", 109, 265, 450, 4700));
        aircrafts.put("PetersF123478ly2", new Aircraft("PetersFly", 110, 72, 440, 1700));
        aircrafts.put("Kaspe3526rsFly", new Aircraft("KaspersFly", 111, 162, 450, 2400));
        aircrafts.put("Peter23152315sFly", new Aircraft("PetersFly", 112, 70, 250, 550));
        aircrafts.put("KristiansF2342341ly", new Aircraft("KristiansFly", 113, 148, 450, 2300));
        aircrafts.put("Henri23512315ksFly", new Aircraft("HenriksFly", 114, 265, 450, 4700));
        aircrafts.put("KaspersF235121356ly1", new Aircraft("KaspersFly1", 115, 72, 440, 1700));
        aircrafts.put("PetersF2135ly1", new Aircraft("PetersFly1", 116, 162, 450, 2400));
        aircrafts.put("KristiansFly1", new Aircraft("KristiansFly1", 117, 70, 250, 550));
        aircrafts.put("HenriksFly1t234", new Aircraft("HenriksFly1", 118, 148, 450, 2300));
        aircrafts.put("KaspersFly2123", new Aircraft("KaspersFly", 119, 265, 450, 4700));
        aircrafts.put("PetersFlywqef2", new Aircraft("PetersFly", 120, 72, 440, 1700));
        aircrafts.put("KaspersFlfew1y", new Aircraft("KaspersFly", 121, 162, 450, 2400));
        aircrafts.put("PetersFl124566y", new Aircraft("PetersFly", 122, 70, 250, 550));
        aircrafts.put("Kristians312Fly", new Aircraft("KristiansFly", 123, 148, 450, 2300));
        aircrafts.put("HenriksF123ly", new Aircraft("HenriksFly", 124, 265, 450, 4700));
        aircrafts.put("KaspersFl1y1", new Aircraft("KaspersFly1", 125, 72, 440, 1700));
        aircrafts.put("PetersF547ly1", new Aircraft("PetersFly1", 126, 162, 450, 2400));
        aircrafts.put("Kristian6576547sFly1", new Aircraft("KristiansFly1", 127, 70, 250, 550));
        aircrafts.put("Henr657567iksFly1", new Aircraft("HenriksFly1", 128, 148, 450, 2300));
        aircrafts.put("KaspersFlyghjgfj2", new Aircraft("KaspersFly", 129, 265, 450, 4700));
        aircrafts.put("PeterfdgjdfksFly2", new Aircraft("PetersFly", 130, 72, 440, 1700));
        aircrafts.put("KaspersFfdjdfgjly", new Aircraft("KaspersFly", 131, 162, 450, 2400));
        aircrafts.put("PetersFvdssghly", new Aircraft("PetersFly", 132, 70, 250, 550));
        aircrafts.put("KrsdvdsvistiansFly", new Aircraft("KristiansFly", 133, 148, 450, 2300));
        aircrafts.put("HenriksF07800ly", new Aircraft("HenriksFly", 134, 265, 450, 4700));
        aircrafts.put("Kaspers6785898Fly1", new Aircraft("KaspersFly1", 135, 72, 440, 1700));
        aircrafts.put("PetersFly8568671", new Aircraft("PetersFly1", 136, 162, 450, 2400));
        aircrafts.put("Kristi5674ansFly1", new Aircraft("KristiansFly1", 137, 70, 250, 550));
        aircrafts.put("HenriksFl35622456y1", new Aircraft("HenriksFly1", 138, 148, 450, 2300));
        aircrafts.put("KaspersFly5675472", new Aircraft("KaspersFly", 139, 265, 450, 4700));
        aircrafts.put("PetersF89789ly2", new Aircraft("PetersFly", 140, 72, 440, 1700));
        aircrafts.put("Kaspe9797rsFly", new Aircraft("KaspersFly", 141, 162, 450, 2400));
        aircrafts.put("Peters567457Fly", new Aircraft("PetersFly", 142, 70, 250, 550));
        aircrafts.put("Kristians78907809Fly", new Aircraft("KristiansFly", 143, 148, 450, 2300));
        aircrafts.put("HenriksF567457ly", new Aircraft("HenriksFly", 144, 265, 450, 4700));
        aircrafts.put("KaspersFl789679y1", new Aircraft("KaspersFly1", 145, 72, 440, 1700));
        aircrafts.put("PetersFly54845681", new Aircraft("PetersFly1", 146, 162, 450, 2400));
        aircrafts.put("KristiansFl6322736y1", new Aircraft("KristiansFly1", 147, 70, 250, 550));
        aircrafts.put("HenriksF63643ly1", new Aircraft("HenriksFly1", 148, 148, 450, 2300));
        aircrafts.put("Kasp236324ersFly2", new Aircraft("KaspersFly", 149, 265, 450, 4700));
        aircrafts.put("Peter2356sFly2", new Aircraft("PetersFly", 150, 72, 440, 1700));
        aircrafts.put("K356236aspersFly", new Aircraft("KaspersFly", 151, 162, 450, 2400));
        aircrafts.put("PetersFl789769y", new Aircraft("PetersFly", 152, 70, 250, 550));
        aircrafts.put("Kristians7532743Fly", new Aircraft("KristiansFly", 153, 148, 450, 2300));
        aircrafts.put("HenriksF324634ly", new Aircraft("HenriksFly", 154, 265, 450, 4700));
        aircrafts.put("KaspersFly234532451", new Aircraft("KaspersFly1", 155, 72, 440, 1700));
        aircrafts.put("PetersFl885456y1", new Aircraft("PetersFly1", 156, 162, 450, 2400));
        aircrafts.put("KristiansF66666ly1", new Aircraft("KristiansFly1", 157, 70, 250, 550));
        aircrafts.put("Henrik112122sFly1", new Aircraft("HenriksFly1", 158, 148, 450, 2300));
        aircrafts.put("KaspersFl7373536y2", new Aircraft("KaspersFly", 159, 265, 450, 4700));
        aircrafts.put("PetersFly768992", new Aircraft("PetersFly", 160, 72, 440, 1700));
        aircrafts.put("KaspersFl0787y", new Aircraft("KaspersFly", 161, 162, 450, 2400));
        aircrafts.put("PetersFl47884y", new Aircraft("PetersFly", 162, 70, 250, 550));
        aircrafts.put("KristiansFl646y", new Aircraft("KristiansFly", 163, 148, 450, 2300));
        aircrafts.put("HenriksF1424126ly", new Aircraft("HenriksFly", 164, 265, 450, 4700));
        aircrafts.put("KaspersFl1244y1", new Aircraft("KaspersFly1", 165, 72, 440, 1700));
        aircrafts.put("PetersF142ly1", new Aircraft("PetersFly1", 166, 162, 450, 2400));
        aircrafts.put("KristiansF43141ly1", new Aircraft("KristiansFly1", 167, 70, 250, 550));
        aircrafts.put("HenriksFl999y1", new Aircraft("HenriksFly1", 168, 148, 450, 2300));
        aircrafts.put("KaspersFl6666y2", new Aircraft("KaspersFly", 169, 265, 450, 4700));
        aircrafts.put("PetersFl78y2", new Aircraft("PetersFly", 170, 72, 440, 1700));
        aircrafts.put("Kaspe5557rsFly", new Aircraft("KaspersFly", 171, 162, 450, 2400));
        aircrafts.put("PetersF66554ly", new Aircraft("PetersFly", 172, 70, 250, 550));
        aircrafts.put("KristiansFl244y", new Aircraft("KristiansFly", 173, 148, 450, 2300));
        aircrafts.put("HenriksFly111", new Aircraft("HenriksFly", 174, 265, 450, 4700));
        aircrafts.put("KaspersFly1211", new Aircraft("KaspersFly1", 175, 72, 440, 1700));
        aircrafts.put("PetersFl324y1", new Aircraft("PetersFly1", 176, 162, 450, 2400));
        aircrafts.put("KristiansF326ly1", new Aircraft("KristiansFly1", 177, 70, 250, 550));
        aircrafts.put("HenriksFly6341", new Aircraft("HenriksFly1", 178, 148, 450, 2300));
        aircrafts.put("KaspersFl473y2", new Aircraft("KaspersFly", 179, 265, 450, 4700));
        aircrafts.put("Peters956Fly2", new Aircraft("PetersFly", 180, 72, 440, 1700));
        aircrafts.put("KaspersF5968ly", new Aircraft("KaspersFly", 181, 162, 450, 2400));
        aircrafts.put("Peter48sFly", new Aircraft("PetersFly", 182, 70, 250, 550));
        aircrafts.put("Kris478tiansFly", new Aircraft("KristiansFly", 183, 148, 450, 2300));
        aircrafts.put("Henr6iksFly", new Aircraft("HenriksFly", 184, 265, 450, 4700));
        aircrafts.put("Kasper84sFly1", new Aircraft("KaspersFly1", 185, 72, 440, 1700));
        aircrafts.put("Pete487rsFl5y1", new Aircraft("PetersFly1", 186, 162, 450, 2400));
        aircrafts.put("Kristia352nsFly1", new Aircraft("KristiansFly1", 187, 70, 250, 550));
        aircrafts.put("Henrik25sFly1", new Aircraft("HenriksFly1", 188, 148, 450, 2300));
        aircrafts.put("Kaspe45rsFly2", new Aircraft("KaspersFly", 189, 265, 450, 4700));
        aircrafts.put("Peter6sFly2", new Aircraft("PetersFly", 190, 72, 440, 1700));
        aircrafts.put("Kaspe7rsFly", new Aircraft("KaspersFly", 191, 162, 450, 2400));
        aircrafts.put("Pete52rsFly", new Aircraft("PetersFly", 192, 70, 250, 550));
        aircrafts.put("Kris1tiansFly", new Aircraft("KristiansFly", 193, 148, 450, 2300));
        aircrafts.put("Henr2iksFly", new Aircraft("HenriksFly", 194, 265, 450, 4700));
        aircrafts.put("Kasp3ersFly1", new Aircraft("KaspersFly1", 195, 72, 440, 1700));
        aircrafts.put("Pete6rsFly1", new Aircraft("PetersFly1", 196, 162, 450, 2400));
        aircrafts.put("Kri5stiansFly1", new Aircraft("KristiansFly1", 197, 70, 250, 550));
        aircrafts.put("Hen4riksFly1", new Aircraft("HenriksFly1", 198, 148, 450, 2300));
        aircrafts.put("Kas2persFly2", new Aircraft("KaspersFly", 199, 265, 450, 4700));
        aircrafts.put("Pet1ersFly2", new Aircraft("PetersFly", 200, 72, 440, 1700));
    }
    
    public HashMap<String, Aircraft> getAllAircrafts() {
        return aircrafts;
    }
    
    public Aircraft getAircraft(String tailnumber) {
        return aircrafts.get(tailnumber);
    }
    
    public static AircraftManager getInstance() {
        if(instance == null)
            instance = new AircraftManager();
        
        return instance;
    }
    
    private static AircraftManager instance;
    
}
