package mediator;

import java.util.HashMap;

public enum CsvFileRepository 
{
    INSTANCE;
    
    private HashMap<String, CsvFile> csvFilesLookup;
    
    private CsvFileRepository()
    {
        csvFilesLookup = new HashMap<>();
    }
    
    public void register(CsvFile csvFile)
    {
        String key = csvFile.getNameWithoutExtension().toLowerCase();
        csvFilesLookup.put(key, csvFile);
    }
    
    public CsvFile getCsvFile(String fileNameWithoutExtension)
    {
        String key = fileNameWithoutExtension.toLowerCase();
        
        if(csvFilesLookup.containsKey(key))
            return csvFilesLookup.get(key);
        return null;
    }
}
