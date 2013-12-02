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
        
        airports.put("LGAV", new Airport(new SphericalPosition(37.94,23.94), "", "LGAV",1)); 
airports.put("ENGM", new Airport(new SphericalPosition(60.2,11.08), "", "ENGM",2)); 
airports.put("EKCH", new Airport(new SphericalPosition(55.62,12.66), "", "EKCH",3)); 
airports.put("ESSA", new Airport(new SphericalPosition(59.65,17.92), "", "ESSA",4)); 
airports.put("ELLX", new Airport(new SphericalPosition(49.62,6.2), "", "ELLX",5)); 
airports.put("EGJB", new Airport(new SphericalPosition(49.43,-2.6), "", "EGJB",6)); 
airports.put("EKBI", new Airport(new SphericalPosition(55.74,9.15), "", "EKBI",7)); 
airports.put("ENBR", new Airport(new SphericalPosition(60.29,5.22), "", "ENBR",8)); 
airports.put("LGTS", new Airport(new SphericalPosition(40.52,22.97), "", "LGTS",9)); 
airports.put("EGJJ", new Airport(new SphericalPosition(49.21,-2.2), "", "EGJJ",10)); 
airports.put("EGLC", new Airport(new SphericalPosition(51.51,0.06), "", "EGLC",11)); 
airports.put("ESSB", new Airport(new SphericalPosition(59.35,17.94), "", "ESSB",12)); 
airports.put("EDDM", new Airport(new SphericalPosition(48.35,11.79), "", "EDDM",13)); 
airports.put("GCLP", new Airport(new SphericalPosition(27.93,-15.39), "", "GCLP",14)); 
airports.put("ENVA", new Airport(new SphericalPosition(63.46,10.92), "", "ENVA",15)); 
airports.put("EFHK", new Airport(new SphericalPosition(60.32,24.96), "", "EFHK",16)); 
airports.put("LFMN", new Airport(new SphericalPosition(43.67,7.21), "", "LFMN",17)); 
airports.put("ENZV", new Airport(new SphericalPosition(58.88,5.64), "", "ENZV",18)); 
airports.put("EIDW", new Airport(new SphericalPosition(53.42,-6.27), "", "EIDW",19)); 
airports.put("EGGW", new Airport(new  SphericalPosition(51.87,-0.37), "", "EGGW",20)); 
airports.put("EGKK", new Airport(new SphericalPosition(51.15,-0.19), "", "EGKK",21)); 
airports.put("EDDK", new Airport(new SphericalPosition(50.87,7.14), "", "EDDK",22)); 
airports.put("LSZH", new Airport(new SphericalPosition(47.46,8.55), "", "LSZH",23)); 
airports.put("UUWW", new Airport(new SphericalPosition(55.6,37.27), "", "UUWW",24)); 
airports.put("LGIR", new Airport(new SphericalPosition(35.34,25.18), "", "LGIR",25)); 
airports.put("LFPG", new Airport(new SphericalPosition(49.01,2.55), "", "LFPG",26)); 
airports.put("LFPB", new Airport(new SphericalPosition(48.97,2.44), "", "LFPB",27)); 
airports.put("LOWW", new Airport(new SphericalPosition(48.11,16.57), "", "LOWW",28)); 
airports.put("GCXO", new Airport(new SphericalPosition(28.48,-16.34), "", "GCXO",29)); 
airports.put("EDDH", new Airport(new SphericalPosition(53.63,9.99), "", "EDDH",30)); 
airports.put("EGPD", new Airport(new SphericalPosition(57.2,-2.2), "", "EGPD",31)); 
airports.put("EDDF", new Airport(new SphericalPosition(50.03,8.57), "", "EDDF",32)); 
airports.put("LIRF", new Airport(new SphericalPosition(41.8,12.24), "", "LIRF",33)); 
airports.put("LSGG", new Airport(new SphericalPosition(46.24,6.11), "", "LSGG",34)); 
airports.put("EBBR", new Airport(new SphericalPosition(50.9,4.48), "", "EBBR",35)); 
airports.put("LGRP", new Airport(new SphericalPosition(36.41,28.09), "", "LGRP",36)); 
airports.put("EGCC", new Airport(new SphericalPosition(53.35,-2.28), "", "EGCC",37)); 
airports.put("EGPH", new Airport(new SphericalPosition(55.95,-3.37), "", "EGPH",38)); 
airports.put("EKYT", new Airport(new SphericalPosition(57.09,9.85), "", "EKYT",39)); 
airports.put("EDDL", new Airport(new SphericalPosition(51.28,6.76), "", "EDDL",40)); 
airports.put("EHAM", new Airport(new SphericalPosition(52.31,4.76), "", "EHAM",41)); 
airports.put("EETN", new Airport(new SphericalPosition(59.41,24.83), "", "EETN",42)); 
airports.put("EDDB", new Airport(new SphericalPosition(52.36,13.5), "", "EDDB",43)); 
airports.put("EDDS", new Airport(new SphericalPosition(48.69,9.22), "", "EDDS",44)); 
airports.put("LEBL", new Airport(new SphericalPosition(41.3,2.08), "", "LEBL",45)); 
airports.put("LEMG", new Airport(new SphericalPosition(36.68,-4.5), "", "LEMG",46)); 
airports.put("EGNX", new Airport(new SphericalPosition(52.83,-1.33), "", "EGNX",47)); 
airports.put("LSZB", new Airport(new SphericalPosition(46.91,7.5), "", "LSZB",48)); 
airports.put("EBAW", new Airport(new SphericalPosition(51.19,4.46), "", "EBAW",49)); 
airports.put("LKPR", new Airport(new SphericalPosition(50.1,14.26), "", "LKPR",50)); 
airports.put("ESGG", new Airport(new SphericalPosition(57.66,12.29), "", "ESGG",51)); 
airports.put("EGLF", new Airport(new SphericalPosition(51.28,-0.78), "", "EGLF",52)); 
airports.put("ENTC", new Airport(new SphericalPosition(69.68,18.92), "", "ENTC",53)); 
airports.put("LCLK", new Airport(new SphericalPosition(34.88,33.63), "", "LCLK",54)); 
airports.put("LEPA", new Airport(new SphericalPosition(39.55,2.74), "", "LEPA",55)); 
airports.put("BGSF", new Airport(new SphericalPosition(67.02,-50.69), "", "BGSF",56)); 
airports.put("ESSV", new Airport(new SphericalPosition(57.66,18.35), "", "ESSV",57)); 
airports.put("EPWA", new Airport(new SphericalPosition(52.17,20.97), "", "EPWA",58)); 
airports.put("EGHI", new Airport(new SphericalPosition(50.95,-1.36), "", "EGHI",59)); 
airports.put("BGGH", new Airport(new SphericalPosition(64.19,-51.68), "", "BGGH",60)); 
airports.put("ESPA", new Airport(new SphericalPosition(65.54,22.12), "", "ESPA",61)); 
airports.put("LGSA", new Airport(new SphericalPosition(35.53,24.15), "", "LGSA",62)); 
airports.put("EDNY", new Airport(new SphericalPosition(47.67,9.51), "", "EDNY",63)); 
airports.put("EHRD", new Airport(new SphericalPosition(51.96,4.44), "", "EHRD",64)); 
airports.put("ESNU", new Airport(new SphericalPosition(63.79,20.28), "", "ESNU",65)); 
airports.put("EGPF", new Airport(new SphericalPosition(55.87,-4.43), "", "EGPF",66)); 
airports.put("EDDT", new Airport(new SphericalPosition(52.56,13.29), "", "EDDT",67)); 
airports.put("EDDN", new Airport(new SphericalPosition(49.5,11.08), "", "EDDN",68)); 
airports.put("EDDW", new Airport(new SphericalPosition(53.05,8.79), "", "EDDW",69)); 
airports.put("LIMC", new Airport(new SphericalPosition(45.63,8.72), "", "LIMC",70)); 
airports.put("LZIB", new Airport(new SphericalPosition(48.17,17.21), "", "LZIB",71)); 
airports.put("EKAH", new Airport(new SphericalPosition(56.3,10.62), "", "EKAH",72)); 
airports.put("ENBO", new Airport(new SphericalPosition(67.27,14.37), "", "ENBO",73)); 
airports.put("LOWS", new Airport(new SphericalPosition(47.79,13), "", "LOWS",74)); 
airports.put("EGNS", new Airport(new SphericalPosition(54.08,-4.62), "", "EGNS",75)); 
airports.put("EGBB", new Airport(new SphericalPosition(52.45,-1.75), "", "EGBB",76)); 
airports.put("ESMS", new Airport(new SphericalPosition(55.55,13.35), "", "ESMS",77)); 
airports.put("LFPO", new Airport(new SphericalPosition(48.72,2.38), "", "LFPO",78)); 
airports.put("GCRR", new Airport(new SphericalPosition(28.95,-13.6), "", "GCRR",79)); 
airports.put("EGKB", new Airport(new SphericalPosition(51.33,0.03), "", "EGKB",80)); 
airports.put("LHBP", new Airport(new SphericalPosition(47.44,19.26), "", "LHBP",81)); 
airports.put("EDDR", new Airport(new SphericalPosition(49.21,7.11), "", "EDDR",82)); 
airports.put("OMDB", new Airport(new SphericalPosition(25.25,55.36), "", "OMDB",83)); 
airports.put("EGTK", new Airport(new SphericalPosition(51.84,-1.32), "", "EGTK",84)); 
airports.put("EDDV", new Airport(new SphericalPosition(52.46,9.68), "", "EDDV",85)); 
airports.put("ULLI", new Airport(new SphericalPosition(59.8,30.26), "", "ULLI",86)); 
airports.put("LOWI", new Airport(new SphericalPosition(47.26,11.34), "", "LOWI",87)); 
airports.put("EGJA", new Airport(new SphericalPosition(49.71,-2.21), "", "EGJA",88)); 
airports.put("EDVE", new Airport(new SphericalPosition(52.32,10.56), "", "EDVE",89)); 
airports.put("EGGD", new Airport(new SphericalPosition(51.38,-2.72), "", "EGGD",90)); 
airports.put("LIML", new Airport(new SphericalPosition(45.45,9.28), "", "LIML",91)); 
airports.put("LFMD", new Airport(new SphericalPosition(43.55,6.95), "", "LFMD",92)); 
airports.put("LTAI", new Airport(new SphericalPosition(36.9,30.79), "", "LTAI",93)); 
airports.put("EKKA", new Airport(new SphericalPosition(56.3,9.12), "", "EKKA",94)); 
airports.put("GCFV", new Airport(new SphericalPosition(28.45,-13.86), "", "GCFV",95)); 
airports.put("LGKR", new Airport(new SphericalPosition(39.6,19.91), "", "LGKR",96)); 
airports.put("EVRA", new Airport(new SphericalPosition(56.92,23.97), "", "EVRA",97)); 
airports.put("EGLL", new Airport(new SphericalPosition(51.48,-0.46), "", "EGLL",98)); 
airports.put("LFRS", new Airport(new SphericalPosition(47.16,-1.61), "", "LFRS",99)); 
airports.put("LEMD", new Airport(new SphericalPosition(40.47,-3.56), "", "LEMD",100)); 
airports.put("LFML", new Airport(new SphericalPosition(43.44,5.22), "", "LFML",101)); 
airports.put("UKKK", new Airport(new SphericalPosition(50.4,30.45), "", "UKKK",102)); 
airports.put("UUDD", new Airport(new SphericalPosition(55.41,37.91), "", "UUDD",103)); 
airports.put("LIPZ", new Airport(new SphericalPosition(45.51,12.35), "", "LIPZ",104)); 
airports.put("LIME", new Airport(new SphericalPosition(45.67,9.7), "", "LIME",105)); 
airports.put("EYVI", new Airport(new SphericalPosition(54.64,25.29), "", "EYVI",106)); 
airports.put("ENAL", new Airport(new SphericalPosition(62.56,6.12), "", "ENAL",107)); 
airports.put("LGSR", new Airport(new SphericalPosition(36.4,25.48), "", "LGSR",108)); 
airports.put("EGPE", new Airport(new SphericalPosition(57.54,-4.05), "", "EGPE",109)); 
airports.put("LROP", new Airport(new SphericalPosition(44.57,26.08), "", "LROP",110)); 
airports.put("GCLA", new Airport(new SphericalPosition(28.63,-17.76), "", "GCLA",111)); 
airports.put("LFSB", new Airport(new SphericalPosition(47.59,7.53), "", "LFSB",112)); 
airports.put("EDSB", new Airport(new SphericalPosition(48.78,8.08), "", "EDSB",113)); 
airports.put("EGPB", new Airport(new SphericalPosition(59.88,-1.29), "", "EGPB",114)); 
airports.put("UUEE", new Airport(new SphericalPosition(55.97,37.41), "", "UUEE",115)); 
airports.put("EGHH", new Airport(new SphericalPosition(50.78,-1.84), "", "EGHH",116)); 
airports.put("LGKO", new Airport(new SphericalPosition(36.79,27.09), "", "LGKO",117)); 
airports.put("EGPO", new Airport(new SphericalPosition(58.22,-6.33), "", "EGPO",118)); 
airports.put("LFBO", new Airport(new SphericalPosition(43.64,1.37), "", "LFBO",119)); 
airports.put("LTBA", new Airport(new SphericalPosition(40.98,28.81), "", "LTBA",120)); 
airports.put("EGSS", new Airport(new SphericalPosition(51.88,0.24), "", "EGSS",121)); 
airports.put("LIEO", new Airport(new SphericalPosition(40.9,9.52), "", "LIEO",122)); 
airports.put("LEAL", new Airport(new SphericalPosition(38.28,-0.56), "", "LEAL",123)); 
airports.put("EKRN", new Airport(new SphericalPosition(55.06,14.76), "", "EKRN",124)); 
airports.put("LATI", new Airport(new SphericalPosition(41.41,19.72), "", "LATI",125)); 
airports.put("LEIB", new Airport(new SphericalPosition(38.87,1.37), "", "LEIB",126)); 
airports.put("ESNN", new Airport(new SphericalPosition(62.53,17.44), "", "ESNN",127)); 
airports.put("EKSB", new Airport(new SphericalPosition(54.96,9.79), "", "EKSB",128)); 
airports.put("LRTR", new Airport(new SphericalPosition(45.81,21.34), "", "LRTR",129)); 
airports.put("LGMT", new Airport(new SphericalPosition(39.06,26.6), "", "LGMT",130)); 
airports.put("EGPA", new Airport(new SphericalPosition(58.96,-2.9), "", "EGPA",131)); 
airports.put("LICJ", new Airport(new SphericalPosition(38.18,13.1), "", "LICJ",132)); 
airports.put("ETSI", new Airport(new SphericalPosition(48.72,11.53), "", "ETSI",133)); 
airports.put("LGMK", new Airport(new SphericalPosition(37.44,25.35), "", "LGMK",134)); 
airports.put("ENEV", new Airport(new SphericalPosition(68.49,16.68), "", "ENEV",135)); 
airports.put("UAAA", new Airport(new SphericalPosition(43.36,77.04), "", "UAAA",136)); 
airports.put("EDDG", new Airport(new SphericalPosition(52.13,7.68), "", "EDDG",137)); 
airports.put("LOWL", new Airport(new SphericalPosition(48.24,14.19), "", "LOWL",138)); 
airports.put("ENML", new Airport(new SphericalPosition(62.74,7.26), "", "ENML",139)); 
airports.put("LIRA", new Airport(new SphericalPosition(41.8,12.6), "", "LIRA",140)); 
airports.put("OIII", new Airport(new SphericalPosition(35.69,51.31), "", "OIII",141)); 
airports.put("EHEH", new Airport(new SphericalPosition(51.45,5.37), "", "EHEH",142)); 
airports.put("LETO", new Airport(new SphericalPosition(40.5,-3.45), "", "LETO",143)); 
airports.put("BGJN", new Airport(new SphericalPosition(69.24,-51.06), "", "BGJN",144)); 
airports.put("LLBG", new Airport(new SphericalPosition(32.01,34.88), "", "LLBG",145)); 
airports.put("UACC", new Airport(new SphericalPosition(51.02,71.47), "", "UACC",146)); 
airports.put("ESGJ", new Airport(new SphericalPosition(57.76,14.07), "", "ESGJ",147)); 
airports.put("ESTA", new Airport(new SphericalPosition(56.29,12.86), "", "ESTA",148)); 
airports.put("EPKT", new Airport(new SphericalPosition(50.47,19.08), "", "EPKT",149)); 
airports.put("UKBB", new Airport(new SphericalPosition(50.34,30.89), "", "UKBB",150)); 
airports.put("LFLL", new Airport(new SphericalPosition(45.73,5.08), "", "LFLL",151)); 
airports.put("LIPE", new Airport(new SphericalPosition(44.53,11.3), "", "LIPE",152)); 
airports.put("EPGD", new Airport(new SphericalPosition(54.38,18.47), "", "EPGD",153)); 
airports.put("EDDC", new Airport(new SphericalPosition(51.13,13.77), "", "EDDC",154)); 
airports.put("OIAW", new Airport(new SphericalPosition(31.34,48.76), "", "OIAW",155)); 
airports.put("ESMX", new Airport(new SphericalPosition(56.93,14.73), "", "ESMX",156)); 
airports.put("ENCN", new Airport(new SphericalPosition(58.2,8.08), "", "ENCN",157)); 
airports.put("ENRY", new Airport(new SphericalPosition(59.38,10.79), "", "ENRY",158)); 
airports.put("ESMT", new Airport(new SphericalPosition(56.69,12.82), "", "ESMT",159)); 
airports.put("EICK", new Airport(new SphericalPosition(51.84,-8.49), "", "EICK",160)); 
airports.put("ENTO", new Airport(new SphericalPosition(59.19,10.26), "", "ENTO",161)); 
airports.put("DNAA", new Airport(new SphericalPosition(9.01,7.26), "", "DNAA",162)); 
airports.put("LSZR", new Airport(new SphericalPosition(47.48,9.56), "", "LSZR",163)); 
airports.put("LFBD", new Airport(new SphericalPosition(44.83,-0.72), "", "LFBD",164)); 
airports.put("EGWU", new Airport(new SphericalPosition(51.55,-0.42), "", "EGWU",165)); 
airports.put("EGSH", new Airport(new SphericalPosition(52.68,1.28), "", "EGSH",166)); 
airports.put("LDZA", new Airport(new SphericalPosition(45.74,16.07), "", "LDZA",167)); 
airports.put("ENFL", new Airport(new SphericalPosition(61.58,5.02), "", "ENFL",168)); 
airports.put("LBSF", new Airport(new SphericalPosition(42.7,23.41), "", "LBSF",169)); 
airports.put("ESOK", new Airport(new SphericalPosition(59.44,13.34), "", "ESOK",170)); 
airports.put("EDFM", new Airport(new SphericalPosition(49.47,8.51), "", "EDFM",171)); 
airports.put("LYBE", new Airport(new SphericalPosition(44.82,20.31), "", "LYBE",172)); 
airports.put("EFOU", new Airport(new SphericalPosition(64.93,25.36), "", "EFOU",173)); 
airports.put("EGFF", new Airport(new SphericalPosition(51.4,-3.34), "", "EGFF",174)); 
airports.put("ESMQ", new Airport(new SphericalPosition(56.69,16.29), "", "ESMQ",175)); 
airports.put("EKVG", new Airport(new SphericalPosition(62.06,-7.28), "", "EKVG",176)); 
airports.put("LICG", new Airport(new SphericalPosition(36.81,11.96), "", "LICG",177)); 
airports.put("EPPO", new Airport(new SphericalPosition(52.42,16.83), "", "EPPO",178)); 
airports.put("EGNT", new Airport(new SphericalPosition(55.04,-1.69), "", "EGNT",179)); 
airports.put("LIRP", new Airport(new SphericalPosition(43.68,10.4), "", "LIRP",180)); 
airports.put("LIPB", new Airport(new SphericalPosition(46.46,11.33), "", "LIPB",181)); 
airports.put("GCTS", new Airport(new SphericalPosition(28.04,-16.57), "", "GCTS",182)); 
airports.put("ESOE", new Airport(new SphericalPosition(59.23,15.04), "", "ESOE",183)); 
airports.put("EDDP", new Airport(new SphericalPosition(51.42,12.24), "", "EDDP",184)); 
airports.put("EDLW", new Airport(new SphericalPosition(51.52,7.61), "", "EDLW",185)); 
airports.put("LGAL", new Airport(new SphericalPosition(40.86,25.96), "", "LGAL",186)); 
airports.put("OEJN", new Airport(new SphericalPosition(21.68,39.16), "", "OEJN",187)); 
airports.put("EINN", new Airport(new SphericalPosition(52.7,-8.92), "", "EINN",188)); 
airports.put("ESSL", new Airport(new SphericalPosition(58.41,15.68), "", "ESSL",189)); 
airports.put("ESNG", new Airport(new SphericalPosition(67.13,20.81), "", "ESNG",190)); 
airports.put("BGSS", new Airport(new SphericalPosition(66.95,-53.73), "", "BGSS",191)); 
airports.put("LFST", new Airport(new SphericalPosition(48.54,7.63), "", "LFST",192)); 
airports.put("EGMC", new Airport(new SphericalPosition(51.57,0.69), "", "EGMC",193)); 
airports.put("LJLJ", new Airport(new SphericalPosition(46.22,14.46), "", "LJLJ",194)); 
airports.put("LPFR", new Airport(new SphericalPosition(37.01,-7.97), "", "LPFR",195)); 
airports.put("ENHD", new Airport(new SphericalPosition(59.34,5.21), "", "ENHD",196)); 
airports.put("ESGP", new Airport(new SphericalPosition(57.78,11.87), "", "ESGP",197)); 
airports.put("EPKK", new Airport(new SphericalPosition(50.08,19.78), "", "EPKK",198)); 
airports.put("UATE", new Airport(new SphericalPosition(43.86,51.09), "", "UATE",199)); 
airports.put("ESSD", new Airport(new SphericalPosition(60.42,15.52), "", "ESSD",200)); 
airports.put("ESSP", new Airport(new SphericalPosition(58.59,16.25), "", "ESSP",201)); 
airports.put("OERK", new Airport(new SphericalPosition(24.96,46.71), "", "OERK",202)); 
airports.put("EBKT", new Airport(new SphericalPosition(50.82,3.21), "", "EBKT",203)); 
airports.put("LGHI", new Airport(new SphericalPosition(38.34,26.14), "", "LGHI",204)); 
airports.put("EIWF", new Airport(new SphericalPosition(52.19,-7.09), "", "EIWF",205)); 
airports.put("LPPT", new Airport(new SphericalPosition(38.77,-9.13), "", "LPPT",206)); 
airports.put("EDMA", new Airport(new SphericalPosition(48.42,10.93), "", "EDMA",207)); 
airports.put("EFVA", new Airport(new SphericalPosition(63.05,21.76), "", "EFVA",208)); 
airports.put("LGSM", new Airport(new SphericalPosition(37.69,26.91), "", "LGSM",209)); 
airports.put("LMML", new Airport(new SphericalPosition(35.86,14.48), "", "LMML",210)); 
airports.put("LGKP", new Airport(new SphericalPosition(35.42,27.15), "", "LGKP",211)); 
airports.put("EGGP", new Airport(new SphericalPosition(53.33,-2.85), "", "EGGP",212)); 
airports.put("EDLP", new Airport(new SphericalPosition(51.61,8.62), "", "EDLP",213)); 
airports.put("DNMM", new Airport(new SphericalPosition(6.58,3.32), "", "DNMM",214)); 
airports.put("EICM", new Airport(new SphericalPosition(53.3,-8.94), "", "EICM",215)); 
airports.put("EGNM", new Airport(new SphericalPosition(53.87,-1.66), "", "EGNM",216)); 
airports.put("EFTU", new Airport(new SphericalPosition(60.51,22.26), "", "EFTU",217)); 
airports.put("LOWG", new Airport(new SphericalPosition(46.99,15.44), "", "LOWG",218)); 
airports.put("ESNZ", new Airport(new SphericalPosition(63.19,14.5), "", "ESNZ",219)); 
airports.put("OKBK", new Airport(new SphericalPosition(29.23,47.98), "", "OKBK",220)); 
airports.put("LIRQ", new Airport(new SphericalPosition(43.81,11.2), "", "LIRQ",221)); 
airports.put("ESNO", new Airport(new SphericalPosition(63.41,18.99), "", "ESNO",222)); 
airports.put("LDSP", new Airport(new SphericalPosition(43.54,16.3), "", "LDSP",223)); 
airports.put("BGAA", new Airport(new SphericalPosition(68.72,-52.78), "", "BGAA",224)); 
airports.put("EDTY", new Airport(new SphericalPosition(49.12,9.78), "", "EDTY",225)); 
airports.put("ESDF", new Airport(new SphericalPosition(56.27,15.26), "", "ESDF",226)); 
airports.put("LFLC", new Airport(new SphericalPosition(45.79,3.16), "", "LFLC",227)); 
airports.put("LRBS", new Airport(new SphericalPosition(44.5,26.1), "", "LRBS",228)); 
airports.put("ENDU", new Airport(new SphericalPosition(69.06,18.54), "", "ENDU",229)); 
airports.put("ENAT", new Airport(new SphericalPosition(69.98,23.37), "", "ENAT",230)); 
airports.put("EGTE", new Airport(new SphericalPosition(50.73,-3.41), "", "EGTE",231)); 
airports.put("LZKZ", new Airport(new SphericalPosition(48.66,21.24), "", "LZKZ",232)); 
airports.put("ESNK", new Airport(new SphericalPosition(63.05,17.77), "", "ESNK",233)); 
airports.put("EFTP", new Airport(new SphericalPosition(61.42,23.59), "", "EFTP",234)); 
airports.put("LIRN", new Airport(new SphericalPosition(40.88,14.29), "", "LIRN",235)); 
airports.put("LIMF", new Airport(new SphericalPosition(45.2,7.65), "", "LIMF",236)); 
airports.put("BIKF", new Airport(new SphericalPosition(63.98,-22.61), "", "BIKF",237)); 
airports.put("BGBW", new Airport(new SphericalPosition(61.16,-45.43), "", "BGBW",238)); 
airports.put("EHBK", new Airport(new SphericalPosition(50.92,5.78), "", "EHBK",239)); 
airports.put("ESKM", new Airport(new SphericalPosition(60.96,14.51), "", "ESKM",240)); 
airports.put("EGSC", new Airport(new SphericalPosition(52.2,0.18), "", "EGSC",241)); 
airports.put("EKRK", new Airport(new SphericalPosition(55.59,12.13), "", "EKRK",242)); 
airports.put("LPPR", new Airport(new SphericalPosition(41.24,-8.68), "", "LPPR",243)); 
airports.put("LIPX", new Airport(new SphericalPosition(45.4,10.89), "", "LIPX",244)); 
airports.put("LEVC", new Airport(new SphericalPosition(39.49,-0.48), "", "LEVC",245)); 
airports.put("HECA", new Airport(new SphericalPosition(30.11,31.41), "", "HECA",246)); 
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
