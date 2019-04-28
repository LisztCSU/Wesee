package com.liszt.wesee_server.controller;

import com.liszt.wesee_server.bean.Nearby;
import com.liszt.wesee_server.bean.ResultList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/nearby")
public class NearbyController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/getNearbyList")
    public ResultList getNearyList(String uid, String mid, HttpServletRequest request) {
        ResultList<Nearby> result = new ResultList<>();
        HttpSession session = request.getSession(false);
        result.setCode(0);
        if (session != null & session.getAttribute("uid") != null && session.getAttribute("uid").equals(uid)) {
            List<String> ids = jdbcTemplate.queryForList("SELECT uid FROM mate WHERE mid=? AND uid <>? AND wanted=? ",String.class, mid, uid,"1");
            if (ids != null && ids.size() > 0) {
                Iterator<String> it = ids.iterator();
                while (it.hasNext()){
                    String str = it.next();
                    List<String> recommends = jdbcTemplate.queryForList("SELECT recommendedAccept FROM setting WHERE id =?",String.class,str);
                    if(recommends.get(0).equals("0")){
                        it.remove();
                    }
                }
                List<String> fars = jdbcTemplate.queryForList("SELECT far FROM setting WHERE id=?", String.class, uid);
                List<String> lats = jdbcTemplate.queryForList("SELECT latitude FROM user_location WHERE id=?", String.class, uid);
                List<String> lngs = jdbcTemplate.queryForList("SELECT longitude FROM user_location WHERE id=?", String.class, uid);
                int far = Integer.parseInt(fars.get(0));
                double lat_a = Double.parseDouble(lats.get(0));
                double lng_a = Double.parseDouble(lngs.get(0));
                List<Nearby> list = new ArrayList<>();
                    for(String id : ids) {
                    List<Nearby> list1 = jdbcTemplate.query("SELECT id AS uid2 ,longitude,latitude FROM user_location WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Nearby.class));
                    if (list1 != null && list1.size() > 0) {
                        double dis = getDistance(lat_a, lng_a, Double.parseDouble(list1.get(0).getLatitude()), Double.parseDouble(list1.get(0).getLongitude()));
                        if (dis <= far) {
                            list1.get(0).setDistance(new DecimalFormat("##0.0").format(dis)+"");
                            list.add(list1.get(0));

                        }
                      }
                    }

                for(Nearby nearby:list){
                    List<String> usernames= jdbcTemplate.queryForList("SELECT username FROM user WHERE id=?",String.class,nearby.getUid2());
                    List<String> nicknames= jdbcTemplate.queryForList("SELECT nickname FROM user WHERE id=?",String.class,nearby.getUid2());
                    nearby.setUsername(usernames.get(0).replaceAll("(\\d{3})", "$1∗∗∗"));
                    nearby.setNickname(nicknames.get(0));
                }
                result.setDataList(list);
                result.setCode(1);
            }
       }

   else {
            result.setCode(-1);
        }

        return result;
    }

    public double getDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
        double pk =  Math.PI / 180;
        double a1 = lat_a * pk;
        double a2 = lng_a * pk;
        double b1 = lat_b * pk;
        double b2 = lng_b * pk;
        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);
        return 6371 * tt;
    }
}
