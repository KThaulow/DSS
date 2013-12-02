/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mediator;

import entities.Airport;
import entities.SphericalPosition;
import java.util.HashMap;

/**
 *
 * @author Henrik
 */
public class AirportManager {
    
    private HashMap<String, Airport> airports;    
    
    private AirportManager() {
        airports = new HashMap<>();
        
        airports.put("LGAV", new Airport(new SphericalPosition(37.94,23.94), "ATHENS/ELEFTHERIOS VENIZELOS", "LGAV",1)); 
airports.put("ENGM", new Airport(new SphericalPosition(60.2,11.08), "OSLO/GARDERMOEN", "ENGM",2)); 
airports.put("EKCH", new Airport(new SphericalPosition(55.62,12.66), "COPENHAGEN/KASTRUP", "EKCH",3)); 
airports.put("ESSA", new Airport(new SphericalPosition(59.65,17.92), "STOCKHOLM/ARLANDA", "ESSA",4)); 
airports.put("ELLX", new Airport(new SphericalPosition(49.62,6.2), "LUXEMBOURG", "ELLX",5)); 
airports.put("EGJB", new Airport(new SphericalPosition(49.43,-2.6), "GUERNSEY", "EGJB",6)); 
airports.put("EKBI", new Airport(new SphericalPosition(55.74,9.15), "BILLUND", "EKBI",7)); 
airports.put("ENBR", new Airport(new SphericalPosition(60.29,5.22), "BERGEN/FLESLAND", "ENBR",8)); 
airports.put("LGTS", new Airport(new SphericalPosition(40.52,22.97), "THESSALONIKI/MAKEDONIA", "LGTS",9)); 
airports.put("EGJJ", new Airport(new SphericalPosition(49.21,-2.2), "JERSEY", "EGJJ",10)); 
airports.put("EGLC", new Airport(new SphericalPosition(51.51,0.06), "LONDON CITY", "EGLC",11)); 
airports.put("ESSB", new Airport(new SphericalPosition(59.35,17.94), "STOCKHOLM/BROMMA", "ESSB",12)); 
airports.put("EDDM", new Airport(new SphericalPosition(48.35,11.79), "MUNICH", "EDDM",13)); 
airports.put("GCLP", new Airport(new SphericalPosition(27.93,-15.39), "GRAN CANARIA", "GCLP",14)); 
airports.put("ENVA", new Airport(new SphericalPosition(63.46,10.92), "TRONDHEIM/VAERNES AB", "ENVA",15)); 
airports.put("EFHK", new Airport(new SphericalPosition(60.32,24.96), "HELSINKI/VANTAA", "EFHK",16)); 
airports.put("LFMN", new Airport(new SphericalPosition(43.67,7.21), "NICE COTE D'AZUR", "LFMN",17)); 
airports.put("ENZV", new Airport(new SphericalPosition(58.88,5.64), "STAVANGER/SOLA", "ENZV",18)); 
airports.put("EIDW", new Airport(new SphericalPosition(53.42,-6.27), "DUBLIN INTL", "EIDW",19)); 
airports.put("EGGW", new Airport(new  SphericalPosition(51.87,-0.37), "LONDON/LUTON", "EGGW",20)); 
airports.put("EGKK", new Airport(new SphericalPosition(51.15,-0.19), "LONDON/GATWICK", "EGKK",21)); 
airports.put("EDDK", new Airport(new SphericalPosition(50.87,7.14), "COLOGNE/BONN", "EDDK",22)); 
airports.put("LSZH", new Airport(new SphericalPosition(47.46,8.55), "ZURICH", "LSZH",23)); 
airports.put("UUWW", new Airport(new SphericalPosition(55.6,37.27), "MOSCOW/VNUKOVO", "UUWW",24)); 
airports.put("LGIR", new Airport(new SphericalPosition(35.34,25.18), "IRAKLION/NIKOS KAZANTZAKIS", "LGIR",25)); 
airports.put("LFPG", new Airport(new SphericalPosition(49.01,2.55), "PARIS/CHARLES DE GAULLE", "LFPG",26)); 
airports.put("LFPB", new Airport(new SphericalPosition(48.97,2.44), "PARIS/LE BOURGET", "LFPB",27)); 
airports.put("LOWW", new Airport(new SphericalPosition(48.11,16.57), "VIENNA/SCHWECHAT", "LOWW",28)); 
airports.put("GCXO", new Airport(new SphericalPosition(28.48,-16.34), "TENERIFE NORTH/LOS RODEOS", "GCXO",29)); 
airports.put("EDDH", new Airport(new SphericalPosition(53.63,9.99), "HAMBURG", "EDDH",30)); 
airports.put("EGPD", new Airport(new SphericalPosition(57.2,-2.2), "ABERDEEN/DYCE", "EGPD",31)); 
airports.put("EDDF", new Airport(new SphericalPosition(50.03,8.57), "FRANKFURT/MAIN", "EDDF",32)); 
airports.put("LIRF", new Airport(new SphericalPosition(41.8,12.24), "ROME/FIUMICINO", "LIRF",33)); 
airports.put("LSGG", new Airport(new SphericalPosition(46.24,6.11), "GENEVA", "LSGG",34)); 
airports.put("EBBR", new Airport(new SphericalPosition(50.9,4.48), "BRUSSELS NATIONAL", "EBBR",35)); 
airports.put("LGRP", new Airport(new SphericalPosition(36.41,28.09), "RODOS/DIAGORAS", "LGRP",36)); 
airports.put("EGCC", new Airport(new SphericalPosition(53.35,-2.28), "MANCHESTER", "EGCC",37)); 
airports.put("EGPH", new Airport(new SphericalPosition(55.95,-3.37), "EDINBURGH", "EGPH",38)); 
airports.put("EKYT", new Airport(new SphericalPosition(57.09,9.85), "AALBORG", "EKYT",39)); 
airports.put("EDDL", new Airport(new SphericalPosition(51.28,6.76), "DUSSELDORF", "EDDL",40)); 
airports.put("EHAM", new Airport(new SphericalPosition(52.31,4.76), "AMSTERDAM/SCHIPHOL", "EHAM",41)); 
airports.put("EETN", new Airport(new SphericalPosition(59.41,24.83), "TALLINN/LENNART MERI", "EETN",42)); 
airports.put("EDDB", new Airport(new SphericalPosition(52.36,13.5), "BERLIN/SCHOENEFELD", "EDDB",43)); 
airports.put("EDDS", new Airport(new SphericalPosition(48.69,9.22), "STUTTGART", "EDDS",44)); 
airports.put("LEBL", new Airport(new SphericalPosition(41.3,2.08), "BARCELONA/EL PRAT", "LEBL",45)); 
airports.put("LEMG", new Airport(new SphericalPosition(36.68,-4.5), "MALAGA/COSTA DEL SOL", "LEMG",46)); 
airports.put("EGNX", new Airport(new SphericalPosition(52.83,-1.33), "DERBY/EAST MIDLANDS", "EGNX",47)); 
airports.put("LSZB", new Airport(new SphericalPosition(46.91,7.5), "BERN/BELP", "LSZB",48)); 
airports.put("EBAW", new Airport(new SphericalPosition(51.19,4.46), "ANTWERPEN/DEURNE", "EBAW",49)); 
airports.put("LKPR", new Airport(new SphericalPosition(50.1,14.26), "PRAGUE/RUZYNE", "LKPR",50)); 
airports.put("ESGG", new Airport(new SphericalPosition(57.66,12.29), "GOTEBORG/LANDVETTER", "ESGG",51)); 
airports.put("EGLF", new Airport(new SphericalPosition(51.28,-0.78), "FARNBOROUGH", "EGLF",52)); 
airports.put("ENTC", new Airport(new SphericalPosition(69.68,18.92), "TROMSO/LAGNES", "ENTC",53)); 
airports.put("LCLK", new Airport(new SphericalPosition(34.88,33.63), "LARNAKA INTL", "LCLK",54)); 
airports.put("LEPA", new Airport(new SphericalPosition(39.55,2.74), "PALMA DE MALLORCA", "LEPA",55)); 
airports.put("BGSF", new Airport(new SphericalPosition(67.02,-50.69), "KANGERLUSSUAQ/SONDRE STROMFJOR", "BGSF",56)); 
airports.put("ESSV", new Airport(new SphericalPosition(57.66,18.35), "VISBY", "ESSV",57)); 
airports.put("EPWA", new Airport(new SphericalPosition(52.17,20.97), "WARSAW/CHOPIN", "EPWA",58)); 
airports.put("EGHI", new Airport(new SphericalPosition(50.95,-1.36), "SOUTHAMPTON INTL", "EGHI",59)); 
airports.put("BGGH", new Airport(new SphericalPosition(64.19,-51.68), "NUUK", "BGGH",60)); 
airports.put("ESPA", new Airport(new SphericalPosition(65.54,22.12), "LULEA/KALLAX AB", "ESPA",61)); 
airports.put("LGSA", new Airport(new SphericalPosition(35.53,24.15), "CHANIA/IOANNIS DASKALOGIANNIS", "LGSA",62)); 
airports.put("EDNY", new Airport(new SphericalPosition(47.67,9.51), "FRIEDRICHSHAFEN", "EDNY",63)); 
airports.put("EHRD", new Airport(new SphericalPosition(51.96,4.44), "ROTTERDAM", "EHRD",64)); 
airports.put("ESNU", new Airport(new SphericalPosition(63.79,20.28), "UMEA", "ESNU",65)); 
airports.put("EGPF", new Airport(new SphericalPosition(55.87,-4.43), "GLASGOW", "EGPF",66)); 
airports.put("EDDT", new Airport(new SphericalPosition(52.56,13.29), "BERLIN/TEGEL", "EDDT",67)); 
airports.put("EDDN", new Airport(new SphericalPosition(49.5,11.08), "NURNBERG", "EDDN",68)); 
airports.put("EDDW", new Airport(new SphericalPosition(53.05,8.79), "BREMEN", "EDDW",69)); 
airports.put("LIMC", new Airport(new SphericalPosition(45.63,8.72), "MILAN/MALPENSA", "LIMC",70)); 
airports.put("LZIB", new Airport(new SphericalPosition(48.17,17.21), "BRATISLAVA/M.R.STEFANIK", "LZIB",71)); 
airports.put("EKAH", new Airport(new SphericalPosition(56.3,10.62), "AARHUS", "EKAH",72)); 
airports.put("ENBO", new Airport(new SphericalPosition(67.27,14.37), "BODO", "ENBO",73)); 
airports.put("LOWS", new Airport(new SphericalPosition(47.79,13), "SALZBURG", "LOWS",74)); 
airports.put("EGNS", new Airport(new SphericalPosition(54.08,-4.62), "ISLE OF MAN", "EGNS",75)); 
airports.put("EGBB", new Airport(new SphericalPosition(52.45,-1.75), "BIRMINGHAM", "EGBB",76)); 
airports.put("ESMS", new Airport(new SphericalPosition(55.55,13.35), "MALMO", "ESMS",77)); 
airports.put("LFPO", new Airport(new SphericalPosition(48.72,2.38), "PARIS/ORLY", "LFPO",78)); 
airports.put("GCRR", new Airport(new SphericalPosition(28.95,-13.6), "LANZAROTE", "GCRR",79)); 
airports.put("EGKB", new Airport(new SphericalPosition(51.33,0.03), "BIGGIN HILL", "EGKB",80)); 
airports.put("LHBP", new Airport(new SphericalPosition(47.44,19.26), "BUDAPEST/LISZT FERENC", "LHBP",81)); 
airports.put("EDDR", new Airport(new SphericalPosition(49.21,7.11), "SAARBRUCKEN", "EDDR",82)); 
airports.put("OMDB", new Airport(new SphericalPosition(25.25,55.36), "DUBAI INTL", "OMDB",83)); 
airports.put("EGTK", new Airport(new SphericalPosition(51.84,-1.32), "OXFORD KIDLINGTON", "EGTK",84)); 
airports.put("EDDV", new Airport(new SphericalPosition(52.46,9.68), "HANNOVER", "EDDV",85)); 
airports.put("ULLI", new Airport(new SphericalPosition(59.8,30.26), "SANKT-PETERBURG/PULKOVO", "ULLI",86)); 
airports.put("LOWI", new Airport(new SphericalPosition(47.26,11.34), "INNSBRUCK", "LOWI",87)); 
airports.put("EGJA", new Airport(new SphericalPosition(49.71,-2.21), "ST ANNES/ALDERNEY", "EGJA",88)); 
airports.put("EDVE", new Airport(new SphericalPosition(52.32,10.56), "BRAUNSCHWEIG-WOLFSBURG", "EDVE",89)); 
airports.put("EGGD", new Airport(new SphericalPosition(51.38,-2.72), "BRISTOL", "EGGD",90)); 
airports.put("LIML", new Airport(new SphericalPosition(45.45,9.28), "MILAN/LINATE", "LIML",91)); 
airports.put("LFMD", new Airport(new SphericalPosition(43.55,6.95), "CANNES/MANDELIEU", "LFMD",92)); 
airports.put("LTAI", new Airport(new SphericalPosition(36.9,30.79), "ANTALYA INTL.", "LTAI",93)); 
airports.put("EKKA", new Airport(new SphericalPosition(56.3,9.12), "KARUP", "EKKA",94)); 
airports.put("GCFV", new Airport(new SphericalPosition(28.45,-13.86), "FUERTEVENTURA", "GCFV",95)); 
airports.put("LGKR", new Airport(new SphericalPosition(39.6,19.91), "KERKIRA/IOANNIS KAPODISTRIAS", "LGKR",96)); 
airports.put("EVRA", new Airport(new SphericalPosition(56.92,23.97), "RIGA", "EVRA",97)); 
airports.put("EGLL", new Airport(new SphericalPosition(51.48,-0.46), "LONDON/HEATHROW", "EGLL",98)); 
airports.put("LFRS", new Airport(new SphericalPosition(47.16,-1.61), "NANTES ATLANTIQUE", "LFRS",99)); 
airports.put("LEMD", new Airport(new SphericalPosition(40.47,-3.56), "MADRID/BARAJAS", "LEMD",100)); 
airports.put("LFML", new Airport(new SphericalPosition(43.44,5.22), "MARSEILLE/PROVENCE", "LFML",101)); 
airports.put("UKKK", new Airport(new SphericalPosition(50.4,30.45), "KYIV/ZHULIANY INTL", "UKKK",102)); 
airports.put("UUDD", new Airport(new SphericalPosition(55.41,37.91), "MOSCOW/DOMODEDOVO", "UUDD",103)); 
airports.put("LIPZ", new Airport(new SphericalPosition(45.51,12.35), "VENICE/TESSERA", "LIPZ",104)); 
airports.put("LIME", new Airport(new SphericalPosition(45.67,9.7), "BERGAMO/ORIO AL SERIO", "LIME",105)); 
airports.put("EYVI", new Airport(new SphericalPosition(54.64,25.29), "VILNIUS INTL.", "EYVI",106)); 
airports.put("ENAL", new Airport(new SphericalPosition(62.56,6.12), "AALESUND/VIGRA", "ENAL",107)); 
airports.put("LGSR", new Airport(new SphericalPosition(36.4,25.48), "SANTORINI", "LGSR",108)); 
airports.put("EGPE", new Airport(new SphericalPosition(57.54,-4.05), "INVERNESS", "EGPE",109)); 
airports.put("LROP", new Airport(new SphericalPosition(44.57,26.08), "BUCHAREST/HENRI COANDA", "LROP",110)); 
airports.put("GCLA", new Airport(new SphericalPosition(28.63,-17.76), "LA PALMA", "GCLA",111)); 
airports.put("LFSB", new Airport(new SphericalPosition(47.59,7.53), "BASLE/MULHOUSE", "LFSB",112)); 
airports.put("EDSB", new Airport(new SphericalPosition(48.78,8.08), "KARLSRUHE/BADEN-BADEN", "EDSB",113)); 
airports.put("EGPB", new Airport(new SphericalPosition(59.88,-1.29), "SUMBURGH", "EGPB",114)); 
airports.put("UUEE", new Airport(new SphericalPosition(55.97,37.41), "MOSCOW/SHEREMETYEVO", "UUEE",115)); 
airports.put("EGHH", new Airport(new SphericalPosition(50.78,-1.84), "BOURNEMOUTH", "EGHH",116)); 
airports.put("LGKO", new Airport(new SphericalPosition(36.79,27.09), "KOS/IPPOKRATIS", "LGKO",117)); 
airports.put("EGPO", new Airport(new SphericalPosition(58.22,-6.33), "STORNOWAY", "EGPO",118)); 
airports.put("LFBO", new Airport(new SphericalPosition(43.64,1.37), "TOULOUSE/BLAGNAC", "LFBO",119)); 
airports.put("LTBA", new Airport(new SphericalPosition(40.98,28.81), "ISTANBUL/ATATURK INTL.", "LTBA",120)); 
airports.put("EGSS", new Airport(new SphericalPosition(51.88,0.24), "LONDON/STANSTED", "EGSS",121)); 
airports.put("LIEO", new Airport(new SphericalPosition(40.9,9.52), "OLBIA/COSTA SMERALDA", "LIEO",122)); 
airports.put("LEAL", new Airport(new SphericalPosition(38.28,-0.56), "ALICANTE", "LEAL",123)); 
airports.put("EKRN", new Airport(new SphericalPosition(55.06,14.76), "BORNHOLM/RONNE", "EKRN",124)); 
airports.put("LATI", new Airport(new SphericalPosition(41.41,19.72), "TIRANA/MOTHER TERESA", "LATI",125)); 
airports.put("LEIB", new Airport(new SphericalPosition(38.87,1.37), "IBIZA", "LEIB",126)); 
airports.put("ESNN", new Airport(new SphericalPosition(62.53,17.44), "SUNDSVALL/TIMRA", "ESNN",127)); 
airports.put("EKSB", new Airport(new SphericalPosition(54.96,9.79), "SONDERBORG", "EKSB",128)); 
airports.put("LRTR", new Airport(new SphericalPosition(45.81,21.34), "TIMISOARA/TRAIAN VUIA", "LRTR",129)); 
airports.put("LGMT", new Airport(new SphericalPosition(39.06,26.6), "MITILINI/ODYSSEAS ELYTIS", "LGMT",130)); 
airports.put("EGPA", new Airport(new SphericalPosition(58.96,-2.9), "KIRKWALL", "EGPA",131)); 
airports.put("LICJ", new Airport(new SphericalPosition(38.18,13.1), "PALERMO/PUNTA RAISI", "LICJ",132)); 
airports.put("ETSI", new Airport(new SphericalPosition(48.72,11.53), "INGOLSTADT/MANCHING", "ETSI",133)); 
airports.put("LGMK", new Airport(new SphericalPosition(37.44,25.35), "MIKONOS", "LGMK",134)); 
airports.put("ENEV", new Airport(new SphericalPosition(68.49,16.68), "HARSTAD-NARVIK/EVENES", "ENEV",135)); 
airports.put("UAAA", new Airport(new SphericalPosition(43.36,77.04), "ALMATY", "UAAA",136)); 
airports.put("EDDG", new Airport(new SphericalPosition(52.13,7.68), "MUNSTER/OSNABRUCK", "EDDG",137)); 
airports.put("LOWL", new Airport(new SphericalPosition(48.24,14.19), "LINZ", "LOWL",138)); 
airports.put("ENML", new Airport(new SphericalPosition(62.74,7.26), "MOLDE/ARO", "ENML",139)); 
airports.put("LIRA", new Airport(new SphericalPosition(41.8,12.6), "ROME/CIAMPINO", "LIRA",140)); 
airports.put("OIII", new Airport(new SphericalPosition(35.69,51.31), "TEHRAN/MEHRABAD INTL", "OIII",141)); 
airports.put("EHEH", new Airport(new SphericalPosition(51.45,5.37), "EINDHOVEN", "EHEH",142)); 
airports.put("LETO", new Airport(new SphericalPosition(40.5,-3.45), "MADRID/TORREJON", "LETO",143)); 
airports.put("BGJN", new Airport(new SphericalPosition(69.24,-51.06), "JAKOBSHAVN/ILULISSAT", "BGJN",144)); 
airports.put("LLBG", new Airport(new SphericalPosition(32.01,34.88), "TEL AVIV/BEN GURION", "LLBG",145)); 
airports.put("UACC", new Airport(new SphericalPosition(51.02,71.47), "ASTANA INTL.", "UACC",146)); 
airports.put("ESGJ", new Airport(new SphericalPosition(57.76,14.07), "JONKOPING", "ESGJ",147)); 
airports.put("ESTA", new Airport(new SphericalPosition(56.29,12.86), "ANGELHOLM", "ESTA",148)); 
airports.put("EPKT", new Airport(new SphericalPosition(50.47,19.08), "KATOWICE/PYRZOWICE", "EPKT",149)); 
airports.put("UKBB", new Airport(new SphericalPosition(50.34,30.89), "KYIV/BORYSPIL' INTL", "UKBB",150)); 
airports.put("LFLL", new Airport(new SphericalPosition(45.73,5.08), "LYON/SAINT EXUPERY", "LFLL",151)); 
airports.put("LIPE", new Airport(new SphericalPosition(44.53,11.3), "BOLOGNA/BORGO PANIGALE", "LIPE",152)); 
airports.put("EPGD", new Airport(new SphericalPosition(54.38,18.47), "GDANSK/LECH WALESA", "EPGD",153)); 
airports.put("EDDC", new Airport(new SphericalPosition(51.13,13.77), "DRESDEN", "EDDC",154)); 
airports.put("OIAW", new Airport(new SphericalPosition(31.34,48.76), "AHWAZ", "OIAW",155)); 
airports.put("ESMX", new Airport(new SphericalPosition(56.93,14.73), "VAEXJOE/KRONOBERG", "ESMX",156)); 
airports.put("ENCN", new Airport(new SphericalPosition(58.2,8.08), "KRISTIANSAND/KJEVIK", "ENCN",157)); 
airports.put("ENRY", new Airport(new SphericalPosition(59.38,10.79), "MOSS/RYGGE", "ENRY",158)); 
airports.put("ESMT", new Airport(new SphericalPosition(56.69,12.82), "HALMSTAD", "ESMT",159)); 
airports.put("EICK", new Airport(new SphericalPosition(51.84,-8.49), "CORK", "EICK",160)); 
airports.put("ENTO", new Airport(new SphericalPosition(59.19,10.26), "SANDEFJORD/TORP", "ENTO",161)); 
airports.put("DNAA", new Airport(new SphericalPosition(9.01,7.26), "ABUJA/NNAMDI AZIKIWE INTL", "DNAA",162)); 
airports.put("LSZR", new Airport(new SphericalPosition(47.48,9.56), "ST GALLEN/ALTENRHEIN", "LSZR",163)); 
airports.put("LFBD", new Airport(new SphericalPosition(44.83,-0.72), "BORDEAUX/MERIGNAC", "LFBD",164)); 
airports.put("EGWU", new Airport(new SphericalPosition(51.55,-0.42), "NORTHOLT", "EGWU",165)); 
airports.put("EGSH", new Airport(new SphericalPosition(52.68,1.28), "NORWICH", "EGSH",166)); 
airports.put("LDZA", new Airport(new SphericalPosition(45.74,16.07), "ZAGREB/PLESO", "LDZA",167)); 
airports.put("ENFL", new Airport(new SphericalPosition(61.58,5.02), "FLORO", "ENFL",168)); 
airports.put("LBSF", new Airport(new SphericalPosition(42.7,23.41), "SOFIA", "LBSF",169)); 
airports.put("ESOK", new Airport(new SphericalPosition(59.44,13.34), "KARLSTAD", "ESOK",170)); 
airports.put("EDFM", new Airport(new SphericalPosition(49.47,8.51), "MANNHEIM CITY", "EDFM",171)); 
airports.put("LYBE", new Airport(new SphericalPosition(44.82,20.31), "BELGRADE/NIKOLA TESLA", "LYBE",172)); 
airports.put("EFOU", new Airport(new SphericalPosition(64.93,25.36), "OULU", "EFOU",173)); 
airports.put("EGFF", new Airport(new SphericalPosition(51.4,-3.34), "CARDIFF", "EGFF",174)); 
airports.put("ESMQ", new Airport(new SphericalPosition(56.69,16.29), "KALMAR", "ESMQ",175)); 
airports.put("EKVG", new Airport(new SphericalPosition(62.06,-7.28), "VAGAR", "EKVG",176)); 
airports.put("LICG", new Airport(new SphericalPosition(36.81,11.96), "PANTELLERIA", "LICG",177)); 
airports.put("EPPO", new Airport(new SphericalPosition(52.42,16.83), "POZNAN/LAWICA", "EPPO",178)); 
airports.put("EGNT", new Airport(new SphericalPosition(55.04,-1.69), "NEWCASTLE", "EGNT",179)); 
airports.put("LIRP", new Airport(new SphericalPosition(43.68,10.4), "PISA/SAN GIUSTO", "LIRP",180)); 
airports.put("LIPB", new Airport(new SphericalPosition(46.46,11.33), "BOLZANO", "LIPB",181)); 
airports.put("GCTS", new Airport(new SphericalPosition(28.04,-16.57), "TENERIFE SOUTH/REINA SOFIA", "GCTS",182)); 
airports.put("ESOE", new Airport(new SphericalPosition(59.23,15.04), "OREBRO", "ESOE",183)); 
airports.put("EDDP", new Airport(new SphericalPosition(51.42,12.24), "LEIPZIG/HALLE", "EDDP",184)); 
airports.put("EDLW", new Airport(new SphericalPosition(51.52,7.61), "DORTMUND", "EDLW",185)); 
airports.put("LGAL", new Airport(new SphericalPosition(40.86,25.96), "ALEXANDROUPOLIS/DIMOKRITOS", "LGAL",186)); 
airports.put("OEJN", new Airport(new SphericalPosition(21.68,39.16), "JEDDAH/KING ABDULAZIZ INTL", "OEJN",187)); 
airports.put("EINN", new Airport(new SphericalPosition(52.7,-8.92), "SHANNON", "EINN",188)); 
airports.put("ESSL", new Airport(new SphericalPosition(58.41,15.68), "LINKOPING/SAAB", "ESSL",189)); 
airports.put("ESNG", new Airport(new SphericalPosition(67.13,20.81), "GALLIVARE", "ESNG",190)); 
airports.put("BGSS", new Airport(new SphericalPosition(66.95,-53.73), "SISIMIUT", "BGSS",191)); 
airports.put("LFST", new Airport(new SphericalPosition(48.54,7.63), "STRASBOURG/ENTZHEIM", "LFST",192)); 
airports.put("EGMC", new Airport(new SphericalPosition(51.57,0.69), "SOUTHEND", "EGMC",193)); 
airports.put("LJLJ", new Airport(new SphericalPosition(46.22,14.46), "LJUBLJANA/BRNIK", "LJLJ",194)); 
airports.put("LPFR", new Airport(new SphericalPosition(37.01,-7.97), "FARO", "LPFR",195)); 
airports.put("ENHD", new Airport(new SphericalPosition(59.34,5.21), "HAUGESUND/KARMOY", "ENHD",196)); 
airports.put("ESGP", new Airport(new SphericalPosition(57.78,11.87), "GOTEBORG/SAEVE AB", "ESGP",197)); 
airports.put("EPKK", new Airport(new SphericalPosition(50.08,19.78), "KRAKOW/BALICE", "EPKK",198)); 
airports.put("UATE", new Airport(new SphericalPosition(43.86,51.09), "AKTAU", "UATE",199)); 
airports.put("ESSD", new Airport(new SphericalPosition(60.42,15.52), "BORLANGE", "ESSD",200)); 
airports.put("ESSP", new Airport(new SphericalPosition(58.59,16.25), "NORRKOPING/KUNGSANGEN", "ESSP",201)); 
airports.put("OERK", new Airport(new SphericalPosition(24.96,46.71), "RIYADH/KING KHALED INTL", "OERK",202)); 
airports.put("EBKT", new Airport(new SphericalPosition(50.82,3.21), "KORTRIJK-WEVELGEM", "EBKT",203)); 
airports.put("LGHI", new Airport(new SphericalPosition(38.34,26.14), "CHIOS/OMIROS", "LGHI",204)); 
airports.put("EIWF", new Airport(new SphericalPosition(52.19,-7.09), "WATERFORD", "EIWF",205)); 
airports.put("LPPT", new Airport(new SphericalPosition(38.77,-9.13), "LISBON", "LPPT",206)); 
airports.put("EDMA", new Airport(new SphericalPosition(48.42,10.93), "AUGSBURG", "EDMA",207)); 
airports.put("EFVA", new Airport(new SphericalPosition(63.05,21.76), "VAASA", "EFVA",208)); 
airports.put("LGSM", new Airport(new SphericalPosition(37.69,26.91), "SAMOS/ARISTARCHOS OF SAMOS", "LGSM",209)); 
airports.put("LMML", new Airport(new SphericalPosition(35.86,14.48), "MALTA/LUQA", "LMML",210)); 
airports.put("LGKP", new Airport(new SphericalPosition(35.42,27.15), "KARPATHOS", "LGKP",211)); 
airports.put("EGGP", new Airport(new SphericalPosition(53.33,-2.85), "LIVERPOOL", "EGGP",212)); 
airports.put("EDLP", new Airport(new SphericalPosition(51.61,8.62), "PADERBORN-LIPPSTADT", "EDLP",213)); 
airports.put("DNMM", new Airport(new SphericalPosition(6.58,3.32), "LAGOS/MURTALA MUHAMMED", "DNMM",214)); 
airports.put("EICM", new Airport(new SphericalPosition(53.3,-8.94), "GALWAY", "EICM",215)); 
airports.put("EGNM", new Airport(new SphericalPosition(53.87,-1.66), "LEEDS BRADFORD", "EGNM",216)); 
airports.put("EFTU", new Airport(new SphericalPosition(60.51,22.26), "TURKU", "EFTU",217)); 
airports.put("LOWG", new Airport(new SphericalPosition(46.99,15.44), "GRAZ", "LOWG",218)); 
airports.put("ESNZ", new Airport(new SphericalPosition(63.19,14.5), "ARE OSTERSUND", "ESNZ",219)); 
airports.put("OKBK", new Airport(new SphericalPosition(29.23,47.98), "KUWAIT INTL", "OKBK",220)); 
airports.put("LIRQ", new Airport(new SphericalPosition(43.81,11.2), "FLORENCE/PERETOLA", "LIRQ",221)); 
airports.put("ESNO", new Airport(new SphericalPosition(63.41,18.99), "ORNSKOLDSVIK", "ESNO",222)); 
airports.put("LDSP", new Airport(new SphericalPosition(43.54,16.3), "SPLIT/KASTELA", "LDSP",223)); 
airports.put("BGAA", new Airport(new SphericalPosition(68.72,-52.78), "AASIAAT", "BGAA",224)); 
airports.put("EDTY", new Airport(new SphericalPosition(49.12,9.78), "SCHWABISCH HALL", "EDTY",225)); 
airports.put("ESDF", new Airport(new SphericalPosition(56.27,15.26), "RONNEBY", "ESDF",226)); 
airports.put("LFLC", new Airport(new SphericalPosition(45.79,3.16), "CLERMONT-FERRAND/AUVERGNE", "LFLC",227)); 
airports.put("LRBS", new Airport(new SphericalPosition(44.5,26.1), "BUCHAREST/BANEASA - AUREL VLAI", "LRBS",228)); 
airports.put("ENDU", new Airport(new SphericalPosition(69.06,18.54), "BARDUFOSS", "ENDU",229)); 
airports.put("ENAT", new Airport(new SphericalPosition(69.98,23.37), "ALTA", "ENAT",230)); 
airports.put("EGTE", new Airport(new SphericalPosition(50.73,-3.41), "EXETER", "EGTE",231)); 
airports.put("LZKZ", new Airport(new SphericalPosition(48.66,21.24), "KOSICE", "LZKZ",232)); 
airports.put("ESNK", new Airport(new SphericalPosition(63.05,17.77), "KRAMFORS-SOLLEFTEA", "ESNK",233)); 
airports.put("EFTP", new Airport(new SphericalPosition(61.42,23.59), "TAMPERE/PIRKKALA", "EFTP",234)); 
airports.put("LIRN", new Airport(new SphericalPosition(40.88,14.29), "NAPLES/CAPODICHINO", "LIRN",235)); 
airports.put("LIMF", new Airport(new SphericalPosition(45.2,7.65), "TORINO/CASELLE", "LIMF",236)); 
airports.put("BIKF", new Airport(new SphericalPosition(63.98,-22.61), "KEFLAVIK", "BIKF",237)); 
airports.put("BGBW", new Airport(new SphericalPosition(61.16,-45.43), "NARSARSUAQ", "BGBW",238)); 
airports.put("EHBK", new Airport(new SphericalPosition(50.92,5.78), "MAASTRICHT/AACHEN", "EHBK",239)); 
airports.put("ESKM", new Airport(new SphericalPosition(60.96,14.51), "MORA SILJAN", "ESKM",240)); 
airports.put("EGSC", new Airport(new SphericalPosition(52.2,0.18), "CAMBRIDGE", "EGSC",241)); 
airports.put("EKRK", new Airport(new SphericalPosition(55.59,12.13), "COPENHAGEN/ROSKILDE", "EKRK",242)); 
airports.put("LPPR", new Airport(new SphericalPosition(41.24,-8.68), "PORTO/FRANCISCO SA CARNEIRO", "LPPR",243)); 
airports.put("LIPX", new Airport(new SphericalPosition(45.4,10.89), "VERONA/VILLAFRANCA", "LIPX",244)); 
airports.put("LEVC", new Airport(new SphericalPosition(39.49,-0.48), "VALENCIA/MANISES", "LEVC",245)); 
airports.put("HECA", new Airport(new SphericalPosition(30.11,31.41), "CAIRO INTL", "HECA",246)); 
    }
    
    public HashMap<String, Airport> getAllAirports() {
        return airports;
    
}

    public Airport getAirport(String icao) {       
        return airports.get(icao);
    }    
    
    public static AirportManager getInstance() {
        
        if(instance == null) {
            instance = new AirportManager();
        }
        
        return instance;
    }
    
    private static AirportManager instance;
    
    
}
