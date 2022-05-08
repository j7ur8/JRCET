package ui;// Java program to validate domain name.
// using regular expression.

import java.util.regex.*;
class test2 {

    public static void main(String[] args) {
        String regex = "<li><span class=\\\"date\\\">([0-9\\-]{25})</span><a href=\\\"/(.*)/\\\"";

        String string="<li><span class=\"date\">2021-04-02-----2022-05-08</span><a href=\"/tieba.baidu.com/\" target=\"_blank\">tieba.baidu.com</a></li>\n" +
                "<li><span class=\"date\">2021-08-27-----2022-05-08</span><a href=\"/ala.baidu.com/\" target=\"_blank\">ala.baidu.com</a></li>\n" +
                "<li><span class=\"date\">2021-04-21-----2022-05-05</span><a href=\"/static.tieba.baidu.com/\" target=\"_blank\">static.tieba.baidu.com</a></li>\n" +
                "<li><span class=\"date\">2021-04-13-----2022-05-04</span><a href=\"/post.n.shifen.com/\" target=\"_blank\">post.n.shifen.com</a></li>\n" +
                "<li><span class=\"date\">2021-04-14-----2022-05-04</span><a href=\"/jump2.bdimg.com/\" target=\"_blank\">jump2.bdimg.com</a></li>\n" +
                "<li><span class=\"date\">2022-05-01-----2022-05-04</span><a href=\"/yoyojie.tieba.com/\" target=\"_blank\">yoyojie.tieba.com</a></li>\n" +
                "<li><span class=\"date\">2020-12-30-----2022-05-03</span><a href=\"/tieba.com/\" target=\"_blank\">tieba.com</a></li>\n" +
                "<li><span class=\"date\">2021-08-25-----2022-05-03</span><a href=\"/v.tieba.com/\" target=\"_blank\">v.tieba.com</a></li>\n" +
                "<li><span class=\"date\">2021-10-28-----2022-05-03</span><a href=\"/nba.baidu.com/\" target=\"_blank\">nba.baidu.com</a></li>\n" +
                "<li><span class=\"date\">2021-05-04-----2022-05-01</span><a href=\"/live.baidu.com/\" target=\"_blank\">live.baidu.com</a></li>\n" +
                "<li><span class=\"date\">2021-06-04-----2022-05-01</span><a href=\"/www.tieba.com/\" target=\"_blank\">www.tieba.com</a></li>\n" +
                "<li><span class=\"date\">2021-03-15-----2022-04-30</span><a href=\"/bjvip.tieba.baidu.com/\" target=\"_blank\">bjvip.tieba.baidu.com</a></li>\n" +
                "<li><span class=\"date\">2021-10-26-----2022-04-30</span><a href=\"/bbs.baidu.com/\" target=\"_blank\">bbs.baidu.com</a></li>\n" +
                "<li><span class=\"date\">2021-12-16-----2022-04-30</span><a href=\"/tieba.baidu.com.cn/\" target=\"_blank\">tieba.baidu.com.cn</a></li>\n" +
                "<li><span class=\"date\">2021-08-16-----2022-04-29</span><a href=\"/fexclick.baidu.com/\" target=\"_blank\">fexclick.baidu.com</a></li>\n" +
                "<li><span class=\"date\">2021-11-09-----2022-04-29</span><a href=\"/partner.tieba.com/\" target=\"_blank\">partner.tieba.com</a></li>\n" +
                "<li><span class=\"date\">2021-04-22-----2022-04-28</span><a href=\"/jump.bdimg.com/\" target=\"_blank\">jump.bdimg.com</a></li>\n" +
                "<li><span class=\"date\">2021-10-28-----2022-02-03</span><a href=\"/nani.baidu.com/\" target=\"_blank\">nani.baidu.com</a></li>\n" +
                "<li><span class=\"date\">2021-07-26-----2021-12-07</span><a href=\"/tieba.baidu.cn/\" target=\"_blank\">tieba.baidu.cn</a></li>\n" +
                "<li><span class=\"date\">2021-06-23-----2021-08-27</span><a href=\"/post.baidu.com/\" target=\"_blank\">post.baidu.com</a></li>\n" +
                "<li><span class=\"date\">2021-05-06-----2021-08-18</span><a href=\"/wapp.baidu.com/\" target=\"_blank\">wapp.baidu.com</a></li>";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            System.out.println(matcher.group(1)+matcher.group(2));
        }
    }
}
