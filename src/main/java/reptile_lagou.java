import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class reptile_lagou {
    static String cityName[] = {"北京","上海","深圳","广州","杭州","成都","南京","武汉","西安","厦门","长沙","苏州","天津","安庆","澳门特别行政区",
            "安阳","鞍山","安康","阿克苏","保定","北海","包头","蚌埠","滨州","保山","巴中","宝鸡","亳州","本溪","巴音郭楞","白城",//31
            "重庆","常州","长春","沧州","赤峰","常德","潮州","郴州","承德","滁州","朝阳","昌吉","东莞","大连","德州","东营","德阳","大庆","达州","大同",
            "大理","定西","德宏","丹东","迪庆","儋州","恩施","鄂州","鄂尔多斯","佛山","福州","阜阳","抚州","抚顺","阜新","防城港","贵阳","桂林","赣州",
            "广安","广元","甘孜藏族自治州","贵港","合肥","哈尔滨","惠州","海口","呼和浩特","邯郸","湖州","淮安","海外","怀化","黄石","衡水","衡阳","菏泽",
            "河源","黄冈","淮北","黄山","呼伦贝尔","鹤壁","淮南","红河","哈密","汉中","河池", "济南","金华","嘉兴","江门","济宁","荆州","吉林",
            "揭阳","吉安","晋中","晋城","九江","酒泉","锦州","荆门","焦作","景德镇","佳木斯","金昌","昆明","开封","克拉玛依","喀什", "廊坊","兰州",
            "临沂","洛阳","拉萨","柳州","聊城","吕梁","龙岩","乐山","连云港","泸州","临汾","丽水","丽江","漯河","六盘水","六安","凉山彝族自治州",
            "娄底","莱芜","辽阳","来宾","临沧","绵阳","茂名","梅州","眉山","马鞍山","牡丹江","宁波","南昌","南宁","南通","南阳","南充","宁德","南平","内江",
            "莆田","濮阳","攀枝花","萍乡","盘锦","平顶山","青岛","泉州","秦皇岛","清远","衢州","庆阳","钦州","齐齐哈尔","曲靖","黔西南","黔南","黔东南","日照",
            "沈阳","石家庄","绍兴","汕头","三亚","宿迁","韶关","商丘","汕尾","邵阳","上饶","十堰","宿州","松原","三门峡","四平","遂宁","绥化","三沙","三明",
            "商洛","随州","太原","唐山","台州","泰安","泰州","台北","天水","天门","铁岭","铜川","吐鲁番","铜仁","通化","通辽","无锡","温州","潍坊",
            "乌鲁木齐","芜湖","威海","渭南","文山","武威","梧州","吴忠","乌兰察布","徐州","西宁","新乡","咸阳","香港特别行政区","邢台","湘潭","襄阳","孝感",
            "许昌","信阳","宣城","咸宁","湘西土家族苗族自治州","忻州","兴安盟","烟台","银川","宜昌","盐城","扬州","岳阳","榆林","宜春","宜宾","运城","阳江",
            "云浮","玉溪","延边","营口","玉林","益阳","雅安","永州","伊犁","延安","鹰潭","阳泉","郑州","珠海","中山","湛江","株洲","镇江","淄博","肇庆",
            "漳州","张家口","遵义","驻马店","长治","舟山","自贡","枣庄","周口","昭通","资阳","张家界"
    };

    //工作信息类
    public static class Job {
        private String money;
        private List<String> companies = new ArrayList<String>();
        private int count;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public List<String> getCompanies() {
            return companies;
        }

        public void setCompanies(List<String> companies) {
            this.companies = companies;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "工资待遇为" + money + "的公司有" + count + "家，他们是：" + companies;
        }
    }

    //主函数
    public static void main(String[] args) {
        //设置webdriver路径
        System.setProperty("webdriver.chrome.driver",reptile_lagou.class.getClassLoader().getResource("chromedriver.exe").getPath());
       //创建webdriver
        WebDriver webDriver = new ChromeDriver();
        Map<String, Job> jobs = new HashMap();
        try {
            //设置筛选条件

            for(int i=1;i<cityName.length;i++)
            {

                // 进入首页
                if(i!=0)
                {

                    try {
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException e) {
                    }
                }
                webDriver.get("https://www.lagou.com/jobs/allCity.html?keyword=%E9%94%80%E5%94%AE%E4%B8%93%E5%91%98&" +
                        "px=default&city=%E5%AE%89%E9%98%B3&positionNum=6&companyNum=0&isCompanySelected=false&labelWords=");
                choseOptions(webDriver, cityName[i]);

                //开始解析页面，分页获取工资
                findMoneyByPagination(webDriver, jobs);
                putOut("\r\n"+cityName[i]);
                //打印工资
                printMoney(jobs);
                jobs.clear();
            }
            webDriver.quit();
            System.out.println("程序执行完毕");
        }
        catch (Exception e) { }
    }

    //打印
    private static void printMoney(Map<String, Job> jobs) {
        ArrayList<Job> list = Lists.newArrayList(jobs.values());
        Collections.sort(list, new Comparator<Job>() {
            public int compare(Job o1, Job o2) {
                return o2.getCount() - o1.getCount();
            }
        });
        for (Job job : list)
        {
            putOut(job);
        }
    }

    //输出方法
    private static void putOut(Job job) {
        File file = new File("C:\\Users\\Administrator\\Desktop\\Reptile.txt");
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        try {
            if (!file.exists())
            {
                boolean hasFile = file.createNewFile();
                if(hasFile){
                    System.out.println("file not exists, create new file");
                }
                fos = new FileOutputStream(file);
            }
            else {
                fos = new FileOutputStream(file,true);
            }

            osw = new OutputStreamWriter(fos, "utf-8");
            osw.write(job.toString()); //写入内容
            osw.write("\r\n");  //换行
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭流
        finally
        {
            try {
                if (osw != null) {
                    osw.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //重载
    private static void putOut(String cityName) {
        File file = new File("C:\\Users\\Administrator\\Desktop\\Reptile.txt");
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        try {
            if (!file.exists())
            {
                boolean hasFile = file.createNewFile();
                if(hasFile){
                    System.out.println("file not exists, create new file");
                }
                fos = new FileOutputStream(file);
            }
            else {
                fos = new FileOutputStream(file,true);
            }

            osw = new OutputStreamWriter(fos, "utf-8");
            osw.write(cityName); //写入内容
            osw.write("\r\n");  //换行
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭流
        finally
        {
            try {
                if (osw != null) {
                    osw.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //解析页面，并进入下一页
    private static void findMoneyByPagination(WebDriver driver, Map<String, Job> map) {
        List<WebElement> jobElements = driver.findElements(By.className("list_item_top"));
        for (WebElement jobElement : jobElements)
        {
            String money = jobElement.findElement(By.className("money")).getText();
            String companyName = jobElement.findElement(By.className("company_name")).getText();
            if (map.containsKey(money))
            {
                Job job = map.get(money);
                job.getCompanies().add(companyName);
                job.setMoney(money);
                job.setCount(job.getCount() + 1);
                map.put(money, job);
            }
            else {
                Job job = new Job();
                job.getCompanies().add(companyName);
                job.setMoney(money);
                job.setCount(1);
                map.put(money, job);
            }
        }
        //如果有点击下一页的选项，进入if
        if(isNextPage(driver,"pager_container")) {
            //点击下一页
            WebElement nextPageBtn = driver.findElement(By.className("pager_next"));
            boolean canClickNextPageBtn = !nextPageBtn.getAttribute("class").contains("pager_next_disabled");
            if (canClickNextPageBtn) {
                nextPageBtn.click();
                try {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e) {
                }
                findMoneyByPagination(driver, map);
            }
        }
        else
        {
            try{
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {
            }
        }
    }

    //判断是否有下一页
    private static boolean isNextPage(WebDriver driver,String string)
    {
        try{
            driver.findElement(By.className(string));
            return true;
        }
        catch (org.openqa.selenium.NoSuchElementException e)
        {
            return false;
        }
    }

    //选择城市
    private static void choseOptions(WebDriver driver,String cityName) {
        // 选择城市
        WebElement cityAuthorElement = driver.findElement(By.xpath("//table[@class='word_list']//a[contains(text(),'" + cityName + "')]"));
        cityAuthorElement.click();
    }
}
