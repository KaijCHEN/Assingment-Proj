import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * fileName: BattleShip.java
 *
 * version: Sep21st2019
 *
 * @author : Kaijia CHEN
 *
 * @author : Ruiqi WANG
 *
 */
public class BattleShip {
    // initialize variables for BattleShip class; verticalIndex / horizontalIndex indicate the specific BattleShip's location
    int horizontalIndex = 0;
    int verticalIndex = 0;
    // initialize char[][] format array to store OceanMap information from OceanMap.txt, map's size was fixed to 5 x 5;
    public static char[][] OceanMap = new char[5][5];
    // initialize char[][] format array to store fake OceanMap
    public static char[][] FakeMap = new char[5][5];
    // initialize coordinate-represent var
    int startX;
    int startY;
    int endX;
    int endY;
    // boolean val to indicate whether BattleShop object has a center; true if length of BattleShip object is odd
    boolean centerExist;
    // int val to indicate BattleShop object center point x
    int centerPointX;
    // int val to indicate BattleShop object center point y
    int centerPointY;
    // int var for object-access the length of this BattleShip
    int shipLength;
    // boolean var indicate whether BattleShip placed vertically or horizontally
    boolean VFHT;
    // boolean indicator for all BattleShips' existence on OceanMap
    static boolean endIndicator = false;
    // object array to hold all BattleShip
    static BattleShip[] battleShip = new BattleShip[20];
    
    
    /**
     * this func is responsible for reading the map information from .txt into OceanMap
     *
     * @param mapLocation : the String format of file's location
     *
     * @return : the verification whether file read successfully
     *
     * @throws FileNotFoundException
     */
    public static boolean readMap(String mapLocation) {
        // local String to hold 5 lines of data
        
        // initialize Scanner for map read
        try{
            // start scan OceanMap and read into OceanMap[][]
            Scanner mapScanner = new Scanner(new File(mapLocation));
            // Scanner read. CONFIGURE?
            while(mapScanner.hasNextLine()){
                // rows of OceanMap
                for (int rowIndex = 0; rowIndex <= 4; rowIndex++){
                    // String format to hold the OceanMap info
                    String mapLine = mapScanner.nextLine();
                    // for-Loop get each single-position of OceanMap from Scanner gained String:: mapLine
                    for(int columnIndex = 0; columnIndex <= 4; columnIndex++){
                        // fill OceanMap and FakeMap
                        OceanMap[rowIndex][columnIndex] = mapLine.charAt(columnIndex);
                        FakeMap[rowIndex][columnIndex] = '.';
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        
        // by default, read nothing from txt
        return true;
    }
    
    

    /**
     * this func is to show fake map
     *
     * @param
     */
    public static boolean showMap(){
        // loop print FakeMap[][]
        for(int temp = 0; temp <= 4; temp++){
            // point to each row
            System.out.println(FakeMap[temp]);
        }
        return true;
    }

    
    /**
     * this func is responsible for control hit movement
     *
     * @param xpos : hit attempt x-axis val
     * @param ypos : hti attempt y-axis val
     * @return : true if hit
     */
    static boolean hitAttempt(int xpos, int ypos){
    String hitpos = String.valueOf(OceanMap[ypos][xpos]);
    // check if hit success
        if(hitpos.equals("x")) {
            // loop-validate which BattleShip is hit
            // local var BattleShip index
            int shipIndex ;
            // loop by create new instance;
            for(shipIndex = 0; shipIndex < battleShip.length; shipIndex++){
                BattleShip shipInstance = battleShip[shipIndex];
                // when BattleShip array reach end, break this loop
                if(shipInstance == null){
                    break;
                }
                // match condition
                if(shipInstance.startX <= xpos && xpos <= shipInstance.endX && shipInstance.startY <= ypos && ypos <= shipInstance.endY){
                    shipInstance.eraseBattleShip(shipInstance, xpos, ypos);
                }
            }
            return true;
        }
        // hit location have no BattleShip
        return false;
    }
    
    
    // check if BattleShip is single placed or vertically placed
    static int[] checkVertical(int curX, int curY, int[] holder){
        // initialize 4 check pos
        int x1 = curX - 1;
        int y1 = curY - 1;
        int x2 = curX - 1;
        int y2 = curY + 1;
        int x4 = curX + 1;
        int y4 = curY + 1;
        int x3 = curX + 1;
        int y3 = curY - 1;

        
        // start check
        // first col
        if(curX == 0){
            // first row and col
            if(curY != 0){
                if(OceanMap[x3][y3] != 'x'){
                
                }
            }else if(curY != 4){
            
            }
            
        }
        return holder;
    }
    
    /**
     * method to check objectShip instance's information
     *
     *
     */

    void checkInstance(BattleShip objectShip){
        System.out.println("CHECK By Object- Head: (" + objectShip.startX + "," + objectShip.startY + ")  Tail: (" + objectShip.endX + "," + objectShip.endX +")" + " Center:" + objectShip.centerExist);
    }
    
    /**
     * check after each round if game is end or initial start
     *
     * @return true if game should be ended
     */
    static boolean checkGameEnd(){
        // locals counter
        int pointCalc = 0;
        // loop check if all pos are plain ocean '.'
        for(int rowIndex = 0; rowIndex <= 4; rowIndex++){
            for(int colIndex = 0; colIndex <= 4; colIndex++){
                if (OceanMap[rowIndex][colIndex] == '.'){
                    pointCalc += 1;
                }
            }
        }
        if (pointCalc == 25){
            return true;
        }
        return false;
    }
    
    
    // func:: dynamically check Battleship and construct objectShip instance
    
    /**
     * dynamically check Battleship and construct objectShip instance
     *
     * @return reut normally, no false case
     */
    static boolean BattleShipCreator(){
        // local counter for row and column
        int localRow;
        int localCol;
        // local var initialize for objectShip construction
        int headX = 0;
        int headY = 0;
        int tailX = 0;
        int tailY = 0;
        boolean headExist = false;
        // dynamic increase for static battleShip[] object-array
        int nameTag = 0;
        
        // loop with row and column
        for(localRow = 0; localRow <= 4; localRow++){
            for(localCol = 0; localCol <= 4; localCol++){
                // if 'x' read
                if(OceanMap[localRow][localCol] == 'x'){
                    // if head Exist
                    if(headExist == true){
                        // inside one ship, do nothing
                        System.out.println("current pointer: (" + localCol + ", " + localRow +")");
                    // if head do not exist, it's new ship
                    }else if(headExist == false){
                        // raise flag, new ship
                        headExist = true;
                        headX = localCol;
                        headY = localRow;
                        // check vertical
                        
                    }
                    // check pos of localCol, if tail is validated
                    if(localCol == 4){
                        tailX = localCol;
                        tailY = localRow;
                        headExist = false;
                        // new ship create, pin new ship into object-array
                        BattleShip objectShip = new BattleShip(headX, headY, tailX, tailY);
                        // validate
                        objectShip.checkInstance(objectShip);
                        System.out.println("local check head: (" + objectShip.startX + "," + objectShip.startY + ")  Tail: (" + objectShip.endX + "," +objectShip.endY + ") Center: " + objectShip.centerExist);
                        battleShip[nameTag] = objectShip;
                        nameTag += 1;
                        // reset
                        tailX = 0;
                        tailY = 0;
                        headX = 0;
                        headY = 0;
                    }
                // if '.' read
                }else if(OceanMap[localRow][localCol] == '.'){
                    // if headExist, end of current ship
                    if(headExist == true){
                        // reach the end of current ship
                        tailX = localCol - 1;
                        tailY = localRow;
                        headExist = false;
                        // new ship create, pin new ship into object-array
                        BattleShip objectShip = new BattleShip(headX, headY, tailX, tailY);
                        // validates
                        objectShip.checkInstance(objectShip);
                        System.out.println("local check head: (" + objectShip.startX + "," + objectShip.startY + ")  Tail: (" + objectShip.endX + "," +objectShip.endY + ") Center: " + objectShip.centerExist);
                        battleShip[nameTag] = objectShip;
                        nameTag += 1;
                    // plain OceanMap
                    }else if(headExist == false){
                        // skip operation
                    }
                }
            }
        }
        return true;
    }
    
    
    // empty constructor
    BattleShip(){
        // null
    }

    
    /**
     * Construct instance of battleship
     * @param startX : start x of this ship
     * @param startY : start y of this ship
     * @param endX : end x of this ship
     * @param endY : end y of this ship
     */
    BattleShip(int startX, int startY, int endX, int endY){
        // create position info of BattleShip object;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        
        // var for record BattleShip's detail info, for further computation; loop / read normally from left to right
        // if BattleShip is placed horizontally
        if((endX - startX + 1 ) >= 1 && (endY - startY + 1 ) == 1 ) {
            this.shipLength = (endX - startX) + 1;
            this.VFHT = true;
        // if BattleShip is placed vertically
        }else if((endX - startX + 1 ) == 1 && (endY - startY + 1 ) > 1 ) {
            this.shipLength = (endY - startY) + 1;
            this.VFHT = false;
        // BattleShip read but coordinate is wrong
        }else{
            System.out.println("\n  = Error in BattleShip Constructor, INFO: startX = " + startX + ", startY = " + startY + ", endX = " + endX + ", endY = " + endY);
        }
        
        // create info of whether Battleship object has a center, depend on BattleShip's length whether odd or even
        if(shipLength % 2 == 0){
            this.centerExist =  false;
        }else{
            this.centerExist = true;
            this.centerPointX = (this.startX + this.endX) / 2;
            this.centerPointY = (this.startY + this.endY) / 2;
        }
        
    }
    
    
    /**
     * erase the battle ship if hit, by part or whole
     *
     * @param shipInstance : the instance of battleship
     * @param xpos : x-val
     * @param ypos ；y-val
     * @return : true if hit success and wiped
     */
    boolean eraseBattleShip(BattleShip shipInstance, int xpos, int ypos){
        // if strike the center
        if (shipInstance.centerExist && centerPointX == xpos && centerPointY == ypos){
            // eliminate whole ship
            for(int loopIndex = 0; loopIndex < this.shipLength; loopIndex++){
                // disable the center point feature
                this.centerExist = false;
                // eliminate the ship
                OceanMap[ypos][shipInstance.startX + loopIndex] = '.';
                // show that ship is destroyed
                FakeMap[ypos][shipInstance.startX + loopIndex] = 'x';
            }
        // miss the center point
        }else{
            // disable the center point feature
            this.centerExist = false;
            // eliminate the ship parts
            OceanMap[ypos][xpos] = '.';
            // show that ship part is destroyed
            FakeMap[ypos][xpos] = 'x';
        }
        // validate
        shipInstance.checkInstance(shipInstance);
        System.out.println("local check head: (" + shipInstance.startX + "," + shipInstance.startY + ")  Tail: (" + shipInstance.endX + "," +shipInstance.endY + ") Center: " + shipInstance.centerExist);
        return true;
    }
    
    
    /**
     * this func:: main() is control BattleShip's behavior
     *
      * @param args : arguments
     */
    public static void main(String[] args) {
        // initialize boolean val for file IO
        boolean readStatus;
        // OceanMap's file location
        String mapLocation = "OceanMap.txt";
        // local var for user's hit-attempt
        int xHit;
        int yHit;
        String checkString;
        
        // read map and judge whether map exist
        readStatus = readMap(mapLocation);
        if(readStatus == false){
            System.out.println("==>      OceanMap.txt  file doesnt exist \n");
            // terminate when file not found
            System.exit(1);
        }
        
        // create BattleShip objects
        BattleShipCreator();
        
        // get user's input coordinate, with different Scanner; extra validate is enabled
        Scanner xScanner = new Scanner(System.in);
        Scanner yScanner = new Scanner(System.in);
        Scanner validateScanner = new Scanner(System.in);
        
        // rule statement
        System.out.println(" \nOriginal Point of Coordination is defined as up-left, x / y both start from 0");
        
        // judge whether OceanMap is joke
        endIndicator = checkGameEnd();
        // Loop-get user's input until all ships destroyed
        while(!endIndicator) {
            // present current FakeMap before next hit
            showMap();
            // request input
            System.out.println("Please type in the x-axis value in OceanMap you want to hit (0-4):  ");
            xHit = xScanner.nextInt();
            System.out.println("Please type in the y-axis value in OceanMap you want to hit (0-4):  ");
            yHit = yScanner.nextInt();
            
            // extra debug input
            System.out.println("type TR to cheat in game");
            checkString = validateScanner.nextLine();
            // cheat code
            if (checkString.equals( "TR")) {
                for (int index = 0; index <= 4; index++) {
                    System.out.println(OceanMap[index]);
                }
            }
            
            boolean returnVal = hitAttempt(xHit, yHit);
            if(returnVal == true){
                // notify user hit success
                System.out.println("HIT Succeed \n");
            }else{
                // notify user hit fail
                System.out.println("HIT Nothing, try again \n");
            }
            // judge whether game is end
            endIndicator = checkGameEnd();
            
        }
        System.out.println("No BattleShip left..\n End of game..");
    }
}
