import java.util.*;

public class HelloWorld
{

    //    private Map<Integer, int[]> content;
    //    private List<Integer> memoryList;
    //    public HelloWorld(){
    //        content = new HashMap<>();
    //        memoryList  = new ArrayList<>();
    //    }
    //    public void append(int[] list) {
    //        int sum =0;
    //        if(list.length == 3){
    //            for(int item: list){
    //                memoryList.add(item);
    //                sum += item;
    //            }
    //            content.put(sum, list);
    //        }else if(list.length == 2){
    //            sum += memoryList.get(memoryList.size()-1);
    //            for(int item: list){
    //                memoryList.add(item);
    //                sum += item;
    //            }
    //            content.put(sum, list);
    //        }else if(list.length == 1){
    //            sum += memoryList.get(memoryList.size()-1);
    //            sum += memoryList.get(memoryList.size()-2);
    //            for(int item: list){
    //                memoryList.add(item);
    //                sum += item;
    //            }
    //            content.put(sum, list);
    //
    //        } else if(list.length == 0){
    //        } else {
    //        }
    //    }
    //
    //    public boolean contains(int total) {
    //        return content.containsKey(total);
    //    }
    //
    //    public static void main(String[] args) {
    //        HelloWorld movingTotal = new HelloWorld();
    //
    //        movingTotal.append(new int[] { 1, 2, 3 });
    //
    //        System.out.println(movingTotal.contains(6));
    //        System.out.println(movingTotal.contains(9));
    //
    //        movingTotal.append(new int[] { 4 });
    //
    //        System.out.println(movingTotal.contains(9));
    //    }

    public static List<String> changeDateFormat(List<String> dates) {
        List<String> outputList = new ArrayList<>();
        for(String date: dates){
            String day="";
            String month="";
            String year="";
            String output;
            String[] dateSplit = date.split("/");
            if(dateSplit.length==3){
                if(dateSplit[0].length()==4){
                    year = dateSplit[0];
                    month = dateSplit[1];
                    day = dateSplit[2];
                } else {
                    year = dateSplit[2];
                    month = dateSplit[1];
                    day = dateSplit[0];
                }
            }
            String[] dateSplit2 = date.split("-");
            if(dateSplit2.length == 3){
                year = dateSplit2[2];
                month = dateSplit2[0];
                day = dateSplit2[1];
            }
            output = year+month+day;
            if(dateSplit.length !=3 && dateSplit2.length !=3){
            }else {
                outputList.add(output);
            }
        }
        return outputList;
    }

    public static void main(String[] args) {
        List<String> dates = changeDateFormat(Arrays.asList("2010/03/30", "15/12/2016", "11-15-2012", "20130720"));
        System.out.println(dates);
        for(String date : dates) {
            System.out.println(date);
        }
    }
}



