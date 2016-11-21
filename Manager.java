public class Manager {

    public static String[] menuArr = {
            "-----Operations for manager menu-----",
            "What kinds of operation would you like to perform?",
            "1. Count the no. of sales record of each salesperson under a specific range on years of experience",
            "2. Show the total sales value of each manufacturer",
            "3. Show the N most popular part",
            "4. Return to the main menu"
    };

    Manager(){
        while (SalesSystem.choice != 4) {
            Functions.printMenu(menuArr);

            SalesSystem.choice = Functions.prompt();
            switch (SalesSystem.choice) {
                case 1:
                    countSalesRec();
                    break;
                case 2:
                    showSalesValManufacturer();
                    break;
                case 3:
                    showNMostPopPart();
                    break;
                case 4:
                    return;
                default:
                    break;
            }
        }
    }

    public static void countSalesRec(){
        System.out.print("Type in the lower bound for years of experience: ");
        int lower = SalesSystem.sc.nextInt();
        System.out.print("Type in the upper bound for years of experience: ");
        int upper = SalesSystem.sc.nextInt();
        SqlOp.countTransBasedOnYrs(lower,upper);
    }

    public static void showSalesValManufacturer(){
            SqlOp.sortAndlistTotalSalesDesc();
    }

    public static void showNMostPopPart(){
        System.out.print("Type in the number of parts: ");
        int n = SalesSystem.sc.nextInt();
        if(n>0){
          SqlOp.findNMosePopularPart(n);
        }else{
          System.out.println("N should be larger than 0");
        }
    }


}
