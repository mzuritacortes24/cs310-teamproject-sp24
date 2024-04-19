package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import org.junit.*;
import static org.junit.Assert.*;
import com.github.cliftonlabs.json_simple.*;

/**
 * 
 * @author willhughes
 * @author mzuritacortes
 */

public class Version2_EmployeeSummaryTest {

    private ReportDAO reportDAO;

    @Before
    public void setup() {

        DAOFactory daoFactory = new DAOFactory("tas.jdbc");
        reportDAO = daoFactory.getReportDAO();

    }
    
    @Test
    public void testEmployeeSummaryCleaning() {
        
        JsonArray jsonExpected = null, jsonActual = null;
        
        try {
            
            int departmentid = 2;
        
            String jsonExpectedString = "[{\"firstname\":\"Ann\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"E215F3DB\",\"shift\":\"Shift 1\",\"middlename\":\"T\",\"active\":\"09/22/2015\",\"department\":\"Cleaning\",\"lastname\":\"Wright\"},{\"firstname\":\"Cruz\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"9186E711\",\"shift\":\"Shift 1\",\"middlename\":\"C\",\"active\":\"01/17/2016\",\"department\":\"Cleaning\",\"lastname\":\"Adams\"},{\"firstname\":\"Curtis\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"2A5620A0\",\"shift\":\"Shift 1\",\"middlename\":\"M\",\"active\":\"10/16/2015\",\"department\":\"Cleaning\",\"lastname\":\"Eaton\"},{\"firstname\":\"Fredrick\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"B09A75D7\",\"shift\":\"Shift 1\",\"middlename\":\"C\",\"active\":\"11/23/2016\",\"department\":\"Cleaning\",\"lastname\":\"Lawrence\"},{\"firstname\":\"Jose\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"DFE4EB13\",\"shift\":\"Shift 1\",\"middlename\":\"S\",\"active\":\"07/26/2016\",\"department\":\"Cleaning\",\"lastname\":\"Black\"}]";
            jsonExpected = (JsonArray)Jsoner.deserialize(jsonExpectedString);
            
            /* Get "Employee Summary" Report (Cleaning Department) */

            String jsonActualString = reportDAO.getEmployeeSummary(departmentid);
            
            jsonActual = (JsonArray)Jsoner.deserialize(jsonActualString);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        /* Compare to Expected Values */
        
        assertNotNull(jsonExpected);
        assertNotNull(jsonActual);
        assertEquals(jsonExpected, jsonActual);

    }

    @Test
    public void testEmployeeSummaryPress() {
        
        JsonArray jsonExpected = null, jsonActual = null;
        
        try {
            
            int departmentid = 7;
        
            String jsonExpectedString = "[{\"firstname\":\"Amy\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"67637925\",\"shift\":\"Shift 1\",\"middlename\":\"B\",\"active\":\"02/02/2017\",\"department\":\"Press\",\"lastname\":\"Brenner\"},{\"firstname\":\"Carol\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"0D87987C\",\"shift\":\"Shift 1\",\"middlename\":\"L\",\"active\":\"01/17/2016\",\"department\":\"Press\",\"lastname\":\"Trice\"},{\"firstname\":\"Claire\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"1C920A23\",\"shift\":\"Shift 1\",\"middlename\":\"T\",\"active\":\"10/04/2015\",\"department\":\"Press\",\"lastname\":\"Hutchison\"},{\"firstname\":\"John\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"0FFA272B\",\"shift\":\"Shift 1\",\"middlename\":\"L\",\"active\":\"10/11/2015\",\"department\":\"Press\",\"lastname\":\"Corwin\"},{\"firstname\":\"Katherine\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"1B2052DE\",\"shift\":\"Shift 1\",\"middlename\":\"H\",\"active\":\"09/22/2015\",\"department\":\"Press\",\"lastname\":\"Sanchez\"},{\"firstname\":\"Kathleen\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"229324A4\",\"shift\":\"Shift 1\",\"middlename\":\"C\",\"active\":\"02/02/2017\",\"department\":\"Press\",\"lastname\":\"Donaldson\"},{\"firstname\":\"Michael\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"8001201A\",\"shift\":\"Shift 1\",\"middlename\":\"E\",\"active\":\"10/11/2015\",\"department\":\"Press\",\"lastname\":\"Stevens\"}]";
            jsonExpected = (JsonArray)Jsoner.deserialize(jsonExpectedString);
            
            /* Get "Employee Summary" Report (Press Department) */

            String jsonActualString = reportDAO.getEmployeeSummary(departmentid);
            
            jsonActual = (JsonArray)Jsoner.deserialize(jsonActualString);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        /* Compare to Expected Values */
        
        assertNotNull(jsonExpected);
        assertNotNull(jsonActual);
        assertEquals(jsonExpected, jsonActual);

    }

    @Test
    public void testEmployeeSummaryShipping() {
        
        JsonArray jsonExpected = null, jsonActual = null;
        
        try {
            
            int departmentid = 8;
        
            String jsonExpectedString = "[{\"firstname\":\"Brian\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"B4BBABEC\",\"shift\":\"Shift 1\",\"middlename\":\"L\",\"active\":\"11/04/2015\",\"department\":\"Shipping\",\"lastname\":\"Lawless\"},{\"firstname\":\"Christine\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"73591F00\",\"shift\":\"Shift 1\",\"middlename\":\"M\",\"active\":\"09/22/2015\",\"department\":\"Shipping\",\"lastname\":\"Gauna\"},{\"firstname\":\"Derrick\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"2305D772\",\"shift\":\"Shift 1\",\"middlename\":\"B\",\"active\":\"09/01/2015\",\"department\":\"Shipping\",\"lastname\":\"Antonio\"},{\"firstname\":\"Elisa\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"B7A6171D\",\"shift\":\"Shift 1\",\"middlename\":\"G\",\"active\":\"09/22/2015\",\"department\":\"Shipping\",\"lastname\":\"Taylor\"},{\"firstname\":\"James\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"9BFCB537\",\"shift\":\"Shift 1\",\"middlename\":\"M\",\"active\":\"09/29/2015\",\"department\":\"Shipping\",\"lastname\":\"Lilly\"},{\"firstname\":\"Jarvis\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"9D527CFB\",\"shift\":\"Shift 1\",\"middlename\":\"B\",\"active\":\"09/22/2015\",\"department\":\"Shipping\",\"lastname\":\"Rodriquez\"},{\"firstname\":\"Kathryn\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"8C4CE4AC\",\"shift\":\"Shift 1\",\"middlename\":\"R\",\"active\":\"12/18/2015\",\"department\":\"Shipping\",\"lastname\":\"Rhoades\"},{\"firstname\":\"Lori\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"4FA55999\",\"shift\":\"Shift 1\",\"middlename\":\"W\",\"active\":\"10/11/2015\",\"department\":\"Shipping\",\"lastname\":\"Davie\"},{\"firstname\":\"Margaret\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"3437588A\",\"shift\":\"Shift 1\",\"middlename\":\"M\",\"active\":\"09/18/2015\",\"department\":\"Shipping\",\"lastname\":\"Mullen\"},{\"firstname\":\"Mitchell\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"E216D413\",\"shift\":\"Shift 1\",\"middlename\":\"C\",\"active\":\"09/29/2015\",\"department\":\"Shipping\",\"lastname\":\"Jones\"},{\"firstname\":\"Nadine\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"B6902696\",\"shift\":\"Shift 1\",\"middlename\":\"P\",\"active\":\"10/09/2015\",\"department\":\"Shipping\",\"lastname\":\"Wright\"},{\"firstname\":\"Nancy\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"EC531DE6\",\"shift\":\"Shift 1\",\"middlename\":\"L\",\"active\":\"09/22/2015\",\"department\":\"Shipping\",\"lastname\":\"Elliott\"},{\"firstname\":\"Robert\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"FF591F68\",\"shift\":\"Shift 1\",\"middlename\":\"K\",\"active\":\"07/28/2015\",\"department\":\"Shipping\",\"lastname\":\"Miller\"},{\"firstname\":\"Salvador\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"CEBCC740\",\"shift\":\"Shift 1\",\"middlename\":\"F\",\"active\":\"09/18/2015\",\"department\":\"Shipping\",\"lastname\":\"Ryan\"},{\"firstname\":\"Tyson\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"E880B82A\",\"shift\":\"Shift 1\",\"middlename\":\"E\",\"active\":\"12/05/2015\",\"department\":\"Shipping\",\"lastname\":\"Williams\"}]";
            jsonExpected = (JsonArray)Jsoner.deserialize(jsonExpectedString);
            
            /* Get "Employee Summary" Report (Shipping Department) */

            String jsonActualString = reportDAO.getEmployeeSummary(departmentid);
            
            jsonActual = (JsonArray)Jsoner.deserialize(jsonActualString);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        /* Compare to Expected Values */
        
        assertNotNull(jsonExpected);
        assertNotNull(jsonActual);
        assertEquals(jsonExpected, jsonActual);

    }
    @Test
    public void testEmployeeSummaryHafting() {
        
        JsonArray jsonExpected = null, jsonActual = null; String va; 
        
        try {
            
            int departmentid = 5;
        
            String jsonExpectedString = "[{\"firstname\":\"Alison\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"9973DBF1\",\"shift\":\"Shift 1\",\"middlename\":\"M\",\"active\":\"08/22/2015\",\"department\":\"Hafting\",\"lastname\":\"Miller\"},{\"firstname\":\"Betty\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"E70AD3D2\",\"shift\":\"Shift 1\",\"middlename\":\"L\",\"active\":\"12/19/2015\",\"department\":\"Hafting\",\"lastname\":\"Decker\"},{\"firstname\":\"Bobbie\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"CB99D1E8\",\"shift\":\"Shift 1\",\"middlename\":\"J\",\"active\":\"01/15/2016\",\"department\":\"Hafting\",\"lastname\":\"Speier\"},{\"firstname\":\"Charles\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"95497F63\",\"shift\":\"Shift 1\",\"middlename\":\"E\",\"active\":\"12/18/2015\",\"department\":\"Hafting\",\"lastname\":\"Andrews\"},{\"firstname\":\"Christine\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"08D745A6\",\"shift\":\"Shift 1\",\"middlename\":\"R\",\"active\":\"11/28/2015\",\"department\":\"Hafting\",\"lastname\":\"Harris\"},{\"firstname\":\"Debra\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"8C0644BA\",\"shift\":\"Shift 1\",\"middlename\":\"T\",\"active\":\"02/06/2016\",\"department\":\"Hafting\",\"lastname\":\"Jones\"},{\"firstname\":\"Diana\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"76B87761\",\"shift\":\"Shift 1\",\"middlename\":\"T\",\"active\":\"10/11/2015\",\"department\":\"Hafting\",\"lastname\":\"Rohrbach\"},{\"firstname\":\"Doris\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"3DA8B226\",\"shift\":\"Shift 1\",\"middlename\":\"R\",\"active\":\"12/19/2015\",\"department\":\"Hafting\",\"lastname\":\"Hamm\"},{\"firstname\":\"Dorothy\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"F1EE0555\",\"shift\":\"Shift 1\",\"middlename\":\"A\",\"active\":\"02/04/2017\",\"department\":\"Hafting\",\"lastname\":\"Montgomery\"},{\"firstname\":\"Edith\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"9BD0258A\",\"shift\":\"Shift 1\",\"middlename\":\"M\",\"active\":\"11/15/2015\",\"department\":\"Hafting\",\"lastname\":\"Hearn\"},{\"firstname\":\"Fay\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"76118CDC\",\"shift\":\"Shift 1\",\"middlename\":\"H\",\"active\":\"11/02/2015\",\"department\":\"Hafting\",\"lastname\":\"Kaiser\"},{\"firstname\":\"Janice\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"FCE87D9F\",\"shift\":\"Shift 1\",\"middlename\":\"W\",\"active\":\"05/14/2016\",\"department\":\"Hafting\",\"lastname\":\"Tucker\"},{\"firstname\":\"Jessie\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"4E6E296E\",\"shift\":\"Shift 1\",\"middlename\":\"B\",\"active\":\"07/28/2015\",\"department\":\"Hafting\",\"lastname\":\"Hart\"},{\"firstname\":\"Joshua\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"12565C60\",\"shift\":\"Shift 1\",\"middlename\":\"E\",\"active\":\"09/11/2015\",\"department\":\"Hafting\",\"lastname\":\"Chapman\"},{\"firstname\":\"Joy\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"BEAFDB2F\",\"shift\":\"Shift 1\",\"middlename\":\"R\",\"active\":\"10/04/2015\",\"department\":\"Hafting\",\"lastname\":\"Clark\"},{\"firstname\":\"Juan\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"398B1563\",\"shift\":\"Shift 2\",\"middlename\":\"T\",\"active\":\"02/21/2017\",\"department\":\"Hafting\",\"lastname\":\"Sanchez\"},{\"firstname\":\"Karen\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"58EB7EA1\",\"shift\":\"Shift 1\",\"middlename\":\"C\",\"active\":\"07/28/2015\",\"department\":\"Hafting\",\"lastname\":\"Stalker\"},{\"firstname\":\"Lawrence\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"408B195F\",\"shift\":\"Shift 1\",\"middlename\":\"D\",\"active\":\"01/26/2017\",\"department\":\"Hafting\",\"lastname\":\"Robinson\"},{\"firstname\":\"Maria\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"E8A58074\",\"shift\":\"Shift 1\",\"middlename\":\"C\",\"active\":\"02/14/2016\",\"department\":\"Hafting\",\"lastname\":\"Brown\"},{\"firstname\":\"Marian\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"55B74EB5\",\"shift\":\"Shift 1\",\"middlename\":\"E\",\"active\":\"09/18/2015\",\"department\":\"Hafting\",\"lastname\":\"Coleman\"},{\"firstname\":\"Mary\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"E06BE060\",\"shift\":\"Shift 1\",\"middlename\":\"A\",\"active\":\"06/04/2016\",\"department\":\"Hafting\",\"lastname\":\"Dixon\"},{\"firstname\":\"Mary\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"4E04B5FE\",\"shift\":\"Shift 1\",\"middlename\":\"J\",\"active\":\"12/07/2016\",\"department\":\"Hafting\",\"lastname\":\"Smith\"},{\"firstname\":\"Mauricio\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"3E9E0E87\",\"shift\":\"Shift 1\",\"middlename\":\"N\",\"active\":\"10/23/2015\",\"department\":\"Hafting\",\"lastname\":\"Patterson\"},{\"firstname\":\"Nicholas\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"124A2DED\",\"shift\":\"Shift 1\",\"middlename\":\"R\",\"active\":\"09/01/2015\",\"department\":\"Hafting\",\"lastname\":\"Ford\"},{\"firstname\":\"Nicholas\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"2CD387C2\",\"shift\":\"Shift 1\",\"middlename\":\"M\",\"active\":\"07/28/2015\",\"department\":\"Hafting\",\"lastname\":\"Horner\"},{\"firstname\":\"Patricia\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"C457EFF7\",\"shift\":\"Shift 1\",\"middlename\":\"T\",\"active\":\"08/28/2015\",\"department\":\"Hafting\",\"lastname\":\"Dam\"},{\"firstname\":\"Patricia\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"8D6362AD\",\"shift\":\"Shift 1\",\"middlename\":\"W\",\"active\":\"03/19/2016\",\"department\":\"Hafting\",\"lastname\":\"McGruder\"},{\"firstname\":\"Phillip\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"DFD9BB5C\",\"shift\":\"Shift 1\",\"middlename\":\"M\",\"active\":\"09/18/2015\",\"department\":\"Hafting\",\"lastname\":\"Gallegos\"},{\"firstname\":\"Raymond\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"D4F37E6F\",\"shift\":\"Shift 1\",\"middlename\":\"V\",\"active\":\"10/16/2015\",\"department\":\"Hafting\",\"lastname\":\"Sanchez\"},{\"firstname\":\"Richard\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"4DAC9951\",\"shift\":\"Shift 1\",\"middlename\":\"M\",\"active\":\"07/28/2015\",\"department\":\"Hafting\",\"lastname\":\"Stein\"},{\"firstname\":\"Sarah\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"CF1D8750\",\"shift\":\"Shift 1\",\"middlename\":\"R\",\"active\":\"11/02/2015\",\"department\":\"Hafting\",\"lastname\":\"Dearman\"},{\"firstname\":\"Sarah\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"9E0476B5\",\"shift\":\"Shift 1\",\"middlename\":\"R\",\"active\":\"02/19/2016\",\"department\":\"Hafting\",\"lastname\":\"Rivers\"},{\"firstname\":\"Sherri\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"D1D2C387\",\"shift\":\"Shift 1\",\"middlename\":\"S\",\"active\":\"12/06/2015\",\"department\":\"Hafting\",\"lastname\":\"Omara\"},{\"firstname\":\"Steven\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"93313988\",\"shift\":\"Shift 1\",\"middlename\":\"M\",\"active\":\"09/11/2015\",\"department\":\"Hafting\",\"lastname\":\"Taylor\"},{\"firstname\":\"Willie\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"860CBBEE\",\"shift\":\"Shift 1\",\"middlename\":\"J\",\"active\":\"08/17/2015\",\"department\":\"Hafting\",\"lastname\":\"Simmons\"}]";
            jsonExpected = (JsonArray)Jsoner.deserialize(jsonExpectedString);
            
            /* Get "Employee Summary" Report (Hafting Department) */

            String jsonActualString = reportDAO.getEmployeeSummary(departmentid);
            
            jsonActual = (JsonArray)Jsoner.deserialize(jsonActualString);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        /* Compare to Expected Values */
        
        assertNotNull(jsonExpected);
        assertNotNull(jsonActual);
        assertEquals(jsonExpected, jsonActual);

    }
    
    @Test
    public void testEmployeeSummaryAll() {
        
        JsonArray jsonExpected = null, jsonActual = null;
        
        try {
        
            String jsonExpectedString = "[\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Micheal\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"E77BFAEA\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"S\",\n" +
                                        "		\"active\": \"08/22/2015\",\n" +
                                        "		\"department\": \"Maintenance\",\n" +
                                        "		\"lastname\": \"Weedman\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Patrick\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"3282F212\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"10/21/2015\",\n" +
                                        "		\"department\": \"Maintenance\",\n" +
                                        "		\"lastname\": \"Smith\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Peter\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"AC239E44\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"H\",\n" +
                                        "		\"active\": \"01/04/2016\",\n" +
                                        "		\"department\": \"Maintenance\",\n" +
                                        "		\"lastname\": \"Cusson\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Chad\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"935B799E\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"A\",\n" +
                                        "		\"active\": \"10/11/2015\",\n" +
                                        "		\"department\": \"Tool and Die\",\n" +
                                        "		\"lastname\": \"Fisher\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Kenneth\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"07901755\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"05/29/2016\",\n" +
                                        "		\"department\": \"Tool and Die\",\n" +
                                        "		\"lastname\": \"Terrell\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Brian\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"B4BBABEC\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"L\",\n" +
                                        "		\"active\": \"11/04/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Lawless\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Christine\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"73591F00\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"09/22/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Gauna\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Derrick\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"2305D772\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"B\",\n" +
                                        "		\"active\": \"09/01/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Antonio\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Elisa\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"B7A6171D\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"G\",\n" +
                                        "		\"active\": \"09/22/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Taylor\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"James\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"9BFCB537\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"09/29/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Lilly\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Jarvis\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"9D527CFB\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"B\",\n" +
                                        "		\"active\": \"09/22/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Rodriquez\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Kathryn\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"8C4CE4AC\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"12/18/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Rhoades\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Lori\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"4FA55999\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"W\",\n" +
                                        "		\"active\": \"10/11/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Davie\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Margaret\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"3437588A\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"09/18/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Mullen\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Mitchell\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"E216D413\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"C\",\n" +
                                        "		\"active\": \"09/29/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Jones\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Nadine\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"B6902696\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"P\",\n" +
                                        "		\"active\": \"10/09/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Wright\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Nancy\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"EC531DE6\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"L\",\n" +
                                        "		\"active\": \"09/22/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Elliott\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Robert\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"FF591F68\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"K\",\n" +
                                        "		\"active\": \"07/28/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Miller\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Salvador\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"CEBCC740\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"F\",\n" +
                                        "		\"active\": \"09/18/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Ryan\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Tyson\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"E880B82A\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"E\",\n" +
                                        "		\"active\": \"12/05/2015\",\n" +
                                        "		\"department\": \"Shipping\",\n" +
                                        "		\"lastname\": \"Williams\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Amy\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"67637925\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"B\",\n" +
                                        "		\"active\": \"02/02/2017\",\n" +
                                        "		\"department\": \"Press\",\n" +
                                        "		\"lastname\": \"Brenner\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Carol\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"0D87987C\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"L\",\n" +
                                        "		\"active\": \"01/17/2016\",\n" +
                                        "		\"department\": \"Press\",\n" +
                                        "		\"lastname\": \"Trice\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Claire\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"1C920A23\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"T\",\n" +
                                        "		\"active\": \"10/04/2015\",\n" +
                                        "		\"department\": \"Press\",\n" +
                                        "		\"lastname\": \"Hutchison\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"John\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"0FFA272B\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"L\",\n" +
                                        "		\"active\": \"10/11/2015\",\n" +
                                        "		\"department\": \"Press\",\n" +
                                        "		\"lastname\": \"Corwin\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Katherine\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"1B2052DE\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"H\",\n" +
                                        "		\"active\": \"09/22/2015\",\n" +
                                        "		\"department\": \"Press\",\n" +
                                        "		\"lastname\": \"Sanchez\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Kathleen\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"229324A4\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"C\",\n" +
                                        "		\"active\": \"02/02/2017\",\n" +
                                        "		\"department\": \"Press\",\n" +
                                        "		\"lastname\": \"Donaldson\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Michael\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"8001201A\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"E\",\n" +
                                        "		\"active\": \"10/11/2015\",\n" +
                                        "		\"department\": \"Press\",\n" +
                                        "		\"lastname\": \"Stevens\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Jennifer\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"ADD650A8\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"T\",\n" +
                                        "		\"active\": \"02/13/2016\",\n" +
                                        "		\"department\": \"Office\",\n" +
                                        "		\"lastname\": \"Taylor\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Laurie\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"4382D92D\",\n" +
                                        "		\"shift\": \"Shift 1 Early Lunch\",\n" +
                                        "		\"middlename\": \"J\",\n" +
                                        "		\"active\": \"10/09/2015\",\n" +
                                        "		\"department\": \"Office\",\n" +
                                        "		\"lastname\": \"Alvarez\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Mildred\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"CEDB6920\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"K\",\n" +
                                        "		\"active\": \"10/22/2016\",\n" +
                                        "		\"department\": \"Office\",\n" +
                                        "		\"lastname\": \"Mills\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Travis\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"C4F37EFF\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"C\",\n" +
                                        "		\"active\": \"02/14/2016\",\n" +
                                        "		\"department\": \"Office\",\n" +
                                        "		\"lastname\": \"Welch\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Alison\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"9973DBF1\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"08/22/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Miller\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Betty\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"E70AD3D2\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"L\",\n" +
                                        "		\"active\": \"12/19/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Decker\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Bobbie\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"CB99D1E8\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"J\",\n" +
                                        "		\"active\": \"01/15/2016\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Speier\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Charles\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"95497F63\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"E\",\n" +
                                        "		\"active\": \"12/18/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Andrews\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Christine\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"08D745A6\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"11/28/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Harris\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Debra\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"8C0644BA\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"T\",\n" +
                                        "		\"active\": \"02/06/2016\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Jones\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Diana\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"76B87761\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"T\",\n" +
                                        "		\"active\": \"10/11/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Rohrbach\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Doris\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"3DA8B226\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"12/19/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Hamm\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Dorothy\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"F1EE0555\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"A\",\n" +
                                        "		\"active\": \"02/04/2017\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Montgomery\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Edith\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"9BD0258A\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"11/15/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Hearn\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Fay\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"76118CDC\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"H\",\n" +
                                        "		\"active\": \"11/02/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Kaiser\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Janice\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"FCE87D9F\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"W\",\n" +
                                        "		\"active\": \"05/14/2016\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Tucker\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Jessie\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"4E6E296E\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"B\",\n" +
                                        "		\"active\": \"07/28/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Hart\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Joshua\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"12565C60\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"E\",\n" +
                                        "		\"active\": \"09/11/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Chapman\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Joy\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"BEAFDB2F\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"10/04/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Clark\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Juan\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"398B1563\",\n" +
                                        "		\"shift\": \"Shift 2\",\n" +
                                        "		\"middlename\": \"T\",\n" +
                                        "		\"active\": \"02/21/2017\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Sanchez\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Karen\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"58EB7EA1\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"C\",\n" +
                                        "		\"active\": \"07/28/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Stalker\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Lawrence\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"408B195F\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"D\",\n" +
                                        "		\"active\": \"01/26/2017\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Robinson\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Maria\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"E8A58074\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"C\",\n" +
                                        "		\"active\": \"02/14/2016\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Brown\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Marian\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"55B74EB5\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"E\",\n" +
                                        "		\"active\": \"09/18/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Coleman\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Mary\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"E06BE060\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"A\",\n" +
                                        "		\"active\": \"06/04/2016\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Dixon\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Mary\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"4E04B5FE\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"J\",\n" +
                                        "		\"active\": \"12/07/2016\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Smith\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Mauricio\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"3E9E0E87\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"N\",\n" +
                                        "		\"active\": \"10/23/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Patterson\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Nicholas\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"124A2DED\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"09/01/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Ford\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Nicholas\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"2CD387C2\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"07/28/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Horner\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Patricia\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"C457EFF7\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"T\",\n" +
                                        "		\"active\": \"08/28/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Dam\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Patricia\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"8D6362AD\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"W\",\n" +
                                        "		\"active\": \"03/19/2016\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"McGruder\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Phillip\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"DFD9BB5C\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"09/18/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Gallegos\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Raymond\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"D4F37E6F\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"V\",\n" +
                                        "		\"active\": \"10/16/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Sanchez\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Richard\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"4DAC9951\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"07/28/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Stein\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Sarah\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"CF1D8750\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"11/02/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Dearman\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Sarah\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"9E0476B5\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"02/19/2016\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Rivers\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Sherri\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"D1D2C387\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"S\",\n" +
                                        "		\"active\": \"12/06/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Omara\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Steven\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"93313988\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"09/11/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Taylor\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Willie\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"860CBBEE\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"J\",\n" +
                                        "		\"active\": \"08/17/2015\",\n" +
                                        "		\"department\": \"Hafting\",\n" +
                                        "		\"lastname\": \"Simmons\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Alvina\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"87176FD7\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"P\",\n" +
                                        "		\"active\": \"01/31/2016\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Cordero\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Amanda\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"9E06A774\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"J\",\n" +
                                        "		\"active\": \"09/01/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Foster\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Amie\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"08D01475\",\n" +
                                        "		\"shift\": \"Shift 2\",\n" +
                                        "		\"middlename\": \"D\",\n" +
                                        "		\"active\": \"01/22/2017\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Littell\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Anna\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"922370AA\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"V\",\n" +
                                        "		\"active\": \"12/13/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Price\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Basil\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"8E5F0240\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"P\",\n" +
                                        "		\"active\": \"08/01/2016\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Thomas\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Bonita\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"690538BA\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"G\",\n" +
                                        "		\"active\": \"10/09/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Snow\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Brad\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"CF697DE6\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"08/28/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Hickmon\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Dustin\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"CEC9A3DA\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"L\",\n" +
                                        "		\"active\": \"11/27/2016\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Hein\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Ernest\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"D2C39273\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"E\",\n" +
                                        "		\"active\": \"08/21/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Buck\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Ethel\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"29C03912\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"H\",\n" +
                                        "		\"active\": \"02/11/2017\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"McKain\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Francis\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"2986FF85\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"07/28/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Black\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Genoveva\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"DF19620C\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"G\",\n" +
                                        "		\"active\": \"11/02/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Forte\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Georgine\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"AB8204A4\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"01/28/2017\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Snively\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Jeffery\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"DD6E2C0C\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"B\",\n" +
                                        "		\"active\": \"11/15/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Rhodes\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Laura\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"88D12DCC\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"A\",\n" +
                                        "		\"active\": \"09/22/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Begaye\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Margaret\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"2A972897\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"09/11/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"White\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Maria\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"A5F194EB\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"J\",\n" +
                                        "		\"active\": \"01/24/2016\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Ybarra\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Matthew\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"28DC3FB8\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"S\",\n" +
                                        "		\"active\": \"07/28/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Woods\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Misty\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"D4A2072B\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"F\",\n" +
                                        "		\"active\": \"10/11/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Ellis\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Nichole\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"CBDE17A7\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"J\",\n" +
                                        "		\"active\": \"02/21/2016\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Fox\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Norman\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"C47A78C1\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"10/11/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"King\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Paul\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"31A25435\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"J\",\n" +
                                        "		\"active\": \"10/09/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Munday\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Raymond\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"2A7F5D99\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"J\",\n" +
                                        "		\"active\": \"12/06/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Palmer\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Scotty\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"7CB9642F\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"L\",\n" +
                                        "		\"active\": \"09/22/2015\",\n" +
                                        "		\"department\": \"Grinding\",\n" +
                                        "		\"lastname\": \"Treat\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Rodney\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"C1E4758D\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"J\",\n" +
                                        "		\"active\": \"10/09/2015\",\n" +
                                        "		\"department\": \"Warehouse\",\n" +
                                        "		\"lastname\": \"Leist\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Ann\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"E215F3DB\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"T\",\n" +
                                        "		\"active\": \"09/22/2015\",\n" +
                                        "		\"department\": \"Cleaning\",\n" +
                                        "		\"lastname\": \"Wright\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Cruz\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"9186E711\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"C\",\n" +
                                        "		\"active\": \"01/17/2016\",\n" +
                                        "		\"department\": \"Cleaning\",\n" +
                                        "		\"lastname\": \"Adams\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Curtis\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"2A5620A0\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"10/16/2015\",\n" +
                                        "		\"department\": \"Cleaning\",\n" +
                                        "		\"lastname\": \"Eaton\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Fredrick\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"B09A75D7\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"C\",\n" +
                                        "		\"active\": \"11/23/2016\",\n" +
                                        "		\"department\": \"Cleaning\",\n" +
                                        "		\"lastname\": \"Lawrence\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Jose\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"DFE4EB13\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"S\",\n" +
                                        "		\"active\": \"07/26/2016\",\n" +
                                        "		\"department\": \"Cleaning\",\n" +
                                        "		\"lastname\": \"Black\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Ali\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"8D9E5710\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"J\",\n" +
                                        "		\"active\": \"05/07/2016\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Melia\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Andrea\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"BE51FA92\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"D\",\n" +
                                        "		\"active\": \"11/08/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Harris\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Dorothy\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"76E920D9\",\n" +
                                        "		\"shift\": \"Shift 2\",\n" +
                                        "		\"middlename\": \"G\",\n" +
                                        "		\"active\": \"01/07/2017\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Lambert\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Earl\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"618072EA\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"08/17/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Perales\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Elaine\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"6CA0FF4A\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"J\",\n" +
                                        "		\"active\": \"09/11/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Pierce\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Ernest\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"99F0C0FA\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"A\",\n" +
                                        "		\"active\": \"02/28/2017\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Kite\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Freda\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"C6C1C0F2\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"C\",\n" +
                                        "		\"active\": \"09/22/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Dickman\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"George\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"021890C0\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"04/02/2016\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Chapell\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Gloria\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"6AA1785E\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"L\",\n" +
                                        "		\"active\": \"09/22/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Ellis\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Harry\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"0B8C3085\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"L\",\n" +
                                        "		\"active\": \"10/30/2016\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"King\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"James\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"D928AFBA\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"E\",\n" +
                                        "		\"active\": \"08/14/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Ali\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"James\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"DFDFE648\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"07/28/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Jinks\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Judy\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"8709982E\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"E\",\n" +
                                        "		\"active\": \"06/27/2016\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Dent\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Kelly\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"E6386C7C\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"K\",\n" +
                                        "		\"active\": \"10/09/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Teel\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Kenneth\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"C0063C4C\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"09/01/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Cain\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Larry\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"55BAF4B1\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"E\",\n" +
                                        "		\"active\": \"08/14/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Hoyt\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Lee\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"639D4185\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"E\",\n" +
                                        "		\"active\": \"10/04/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Gaines\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Lillian\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"CEA28723\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"A\",\n" +
                                        "		\"active\": \"09/04/2016\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Claude\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Marcus\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"45E5059F\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"K\",\n" +
                                        "		\"active\": \"08/22/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Osborne\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Mary\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"B5A117E9\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"C\",\n" +
                                        "		\"active\": \"01/03/2016\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Sullivan\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Matthew\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"D2CC71D4\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"J\",\n" +
                                        "		\"active\": \"12/05/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Lawson\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Robert\",\n" +
                                        "		\"employeetype\": \"Full-Time Employee\",\n" +
                                        "		\"badgeid\": \"4C459F1E\",\n" +
                                        "		\"shift\": \"Shift 2\",\n" +
                                        "		\"middlename\": \"B\",\n" +
                                        "		\"active\": \"01/07/2017\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Knaus\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Ronald\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"6C0D1549\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"W\",\n" +
                                        "		\"active\": \"09/22/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Franklin\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Rosalee\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"82A8539F\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"T\",\n" +
                                        "		\"active\": \"09/18/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Patterson\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Rose\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"29C3C7D4\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"M\",\n" +
                                        "		\"active\": \"11/02/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Gomez\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Rose\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"C278A564\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"01/08/2016\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Hill\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Sandra\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"BAD139D6\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"R\",\n" +
                                        "		\"active\": \"09/01/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Sutherland\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Tamara\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"BD9BB983\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"G\",\n" +
                                        "		\"active\": \"11/28/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Dahl\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Theresa\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"0886BF12\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"E\",\n" +
                                        "		\"active\": \"08/14/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Gibson\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Thomas\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"8A90B9A3\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"S\",\n" +
                                        "		\"active\": \"08/28/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Kawamoto\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Thomas\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"C8E646D8\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"L\",\n" +
                                        "		\"active\": \"10/11/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Merrick\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Tracy\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"1D52BFA2\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"A\",\n" +
                                        "		\"active\": \"10/09/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Lowery\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Veronica\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"8DB842EF\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"C\",\n" +
                                        "		\"active\": \"08/22/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Arney\"\n" +
                                        "	},\n" +
                                        "	{\n" +
                                        "		\"firstname\": \"Wm\",\n" +
                                        "		\"employeetype\": \"Temporary Employee\",\n" +
                                        "		\"badgeid\": \"3C03B8D7\",\n" +
                                        "		\"shift\": \"Shift 1\",\n" +
                                        "		\"middlename\": \"N\",\n" +
                                        "		\"active\": \"09/01/2015\",\n" +
                                        "		\"department\": \"Assembly\",\n" +
                                        "		\"lastname\": \"Howard\"\n" +
                                        "	}\n" +
                                        "]";
            jsonExpected = (JsonArray)Jsoner.deserialize(jsonExpectedString);
            
            /* Get "Employee Summary" Report (For all) */

            String jsonActualString = reportDAO.getEmployeeSummary(null);
            
            jsonActual = (JsonArray)Jsoner.deserialize(jsonActualString);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        /* Compare to Expected Values */
        
        assertNotNull(jsonExpected);
        assertNotNull(jsonActual);
        assertEquals(jsonExpected, jsonActual);

    }
    
}