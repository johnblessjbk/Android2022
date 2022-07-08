
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="PackChain.NoobChain"%>
<%@page import="PackChain.Block"%>
<%@page import="com.example.blockchain.CODE_ALGORITHM.AES"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="Connection.dbconnection"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Iterator"%>
<%
    dbconnection con = new dbconnection();
    String key = request.getParameter("key").trim();
    System.out.println(key);
    if (key.equals("login")) {
        String info = "";
        String qry = "select *from `login` where name='" + request.getParameter("uname") + "'and pass='" + request.getParameter("pass") + "'";
        System.out.println("qry=" + qry);
        Iterator it = con.getData(qry).iterator();
        if (it.hasNext()) {
            Vector v = (Vector) it.next();
            info = v.get(3) + "#" + v.get(5) + "#" + v.get(2);
            System.out.println("yes id=" + info);
            out.print(info);
        } else {
            System.out.println("else id=" + info);
            out.print("failed");
        }
    }
    if (key.equals("UserReg")) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String addres = request.getParameter("addres");
        String phone = request.getParameter("phone");
        String pass = request.getParameter("pass");
        String qry = "INSERT INTO `userreg`(`name`,`email`,`addres`,`phone`,`pass`) VALUES ('" + name + "','" + email + "','" + addres + "','" + phone + "','" + pass + "')";
        String qry1 = "insert into login (name,pass,type,status,uid)values('" + email + "','" + pass + "','user','1',(select max(uid)from userreg))";
        if (con.putData(qry) > 0 && con.putData(qry1) > 0) {
            out.print("successful");
        } else {
            out.print("failed");
        }
    }
  if (key.equals("DoctorReg")) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String addres = request.getParameter("addres");
        String phone = request.getParameter("phone");
        String pass = request.getParameter("pass");
        String qry = "INSERT INTO `doctorreg`(`name`,`email`,`addres`,`phone`,`pass`) VALUES ('" + name + "','" + email + "','" + addres + "','" + phone + "','" + pass + "')";
        String qry1 = "insert into login (name,pass,type,status,uid)values('" + email + "','" + pass + "','doctor','1',(select max(did)from doctorreg))";
        if (con.putData(qry) > 0 && con.putData(qry1) > 0) {
            out.print("successful");
        } else {
            out.print("failed");
        }
    }

    if (key.equals("addPatient")) {

        String pid = request.getParameter("pid");
        String userid = request.getParameter("uid");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String age = request.getParameter("age");
        String addres = request.getParameter("address");
        String phone = request.getParameter("phone");
        String ukey = request.getParameter("userkey");
        String decryptDATA = AES.decrypt(pid, "secretKey");
        System.out.println("Encrption vallue" + pid);
        System.out.println("Decryption vallue" + decryptDATA);

        String qry = "INSERT INTO `addpatient`(userid,pid,`name`,`email`,age,`address`,`phone`,`ukey`) VALUES ('" + userid + "','" + pid + "','" + name + "','" + email + "','" + age + "','" + addres + "','" + phone + "','" + ukey + "')";
        //String qry1 = "insert into login (name,pass,type,status,uid)values('" + decryptDATA + "','" + ukey + "','doctor','1',(select max(patid)from addpatient))";

        if (con.putData(qry) > 0) {

            out.print("successful");
            System.out.println("Okay");
        } else {
            out.print("failed");
            System.out.println("no");
        }
    }
    //GetPlantsList
    if (key.equals("gettingusernameS")) {
        String info = "";
        JSONArray array = new JSONArray();
        String uid = request.getParameter("uid");
        String qry = "SELECT `name` FROM`addpatient` WHERE `addpatient`.`userid`='" + uid + "'";
        System.out.println("qry=" + qry);
        Iterator it = con.getData(qry).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {

                Vector v = (Vector) it.next();
                String decryptDATA = AES.decrypt(v.get(0).toString(), "secretKey");

                info = decryptDATA + "#";
                array.add(info);
                out.println(info);
                System.out.println(info);
            }
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

    if (key.equals("addMedical")) {

        String BATIENTNAMEq = request.getParameter("BATIENTNAMEq").trim();
        String uid = request.getParameter("uid");
        String blood = request.getParameter("blood");
        String count = request.getParameter("count");
        String clostro = request.getParameter("cholestrol");
        String link = request.getParameter("link");
        String image = request.getParameter("imagess");
        System.out.println(BATIENTNAMEq);
        String our = AES.encrypt(BATIENTNAMEq, "secretKey");
        System.out.println(our);
        String qry = "INSERT INTO `addmedical`(`pname`,`blood`,`count`,`cholestrol`,`link`,`image`,`uid`)values('" + our + "','" + blood + "','" + count + "','" + clostro + "','" + link + "','" + image + "','" + uid + "')";
        if (con.putData(qry) > 0) {
            out.print("successful");
            System.out.println("Okay");
        } else {
            out.print("failed");
            System.out.println("no");
        }
    }

    if (key.equals("GetPatientList")) {
        JSONArray array = new JSONArray();
        String uid = request.getParameter("userid");
        String qry = "SELECT `pid`,`name` FROM `addpatient` WHERE `userid`='" + uid + "'";
        System.out.println("qry=" + qry);
        Iterator it = con.getData(qry).iterator();
        if (it.hasNext()) {

            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();

                obj.put("id", v.get(0).toString().trim());
                String g = v.get(1).toString();

                String datae = AES.decrypt(g, "secretKey");
                obj.put("patientname", datae.trim());
                array.add(obj);

            }
            out.println(array);

        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }
    
      if (key.equals("getallpatientrecordat")) {
        JSONArray array = new JSONArray();
      // String uid = request.getParameter("userid");
        String qry = "SELECT `pid`,`name` FROM `addpatient`";
        System.out.println("qry=" + qry);
        Iterator it = con.getData(qry).iterator();
        if (it.hasNext()) {

            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();

                obj.put("id", v.get(0).toString().trim());
                String g = v.get(1).toString();

                String datae = AES.decrypt(g, "secretKey");
                obj.put("patientname", datae.trim());
                array.add(obj);

            }
            out.println(array);

        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }
    
    if (key.equals("GetMedicalList")) {
        JSONArray array = new JSONArray();
        String uid = request.getParameter("userid");
        String pname = request.getParameter("pname");
        String pENC = AES.encrypt(pname, "secretKey");
        System.out.println("pname ---" + pname);
        System.out.println("pname Encryption ---" + pENC);

        String qry = "SELECT * from addmedical WHERE `addmedical`.`pname`='" + pENC + "' AND `addmedical`.`uid`='" + uid + "'";
        System.out.println("qry=" + qry);
        Iterator it = con.getData(qry).iterator();
        if (it.hasNext()) {

            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("id", "j");
                obj.put("patientname", v.get(1).toString().trim());
                obj.put("count", v.get(2).toString().trim());
                obj.put("blood", v.get(3).toString().trim());
                String getvalue=v.get(6).toString().trim();
                 String substr=getvalue.substring(0,2000);
                obj.put("link", v.get(5).toString().trim());
                obj.put("image",substr );
                obj.put("choles", v.get(4).toString().trim());
                array.add(obj);
                System.out.println(array);
            }
            out.println(array);

        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

    if (key.equals("patientprofile")) {
        String info = "";
        String uid = request.getParameter("userid");
        String ukey = request.getParameter("userkey");
        String qry = "select *from `addpatient` where  ukey='" + ukey + "'";
        System.out.println("qry=" + qry);
        Iterator it = con.getData(qry).iterator();
        if (it.hasNext()) {
            Vector v = (Vector) it.next();
            info = v.get(2) + "#" + v.get(3) + "#" + v.get(4) + "#" + v.get(5) + "#" + v.get(6) + "#" + v.get(7) + "#" + v.get(8);
            System.out.println("yes id=" + info);
            out.print(info);
        } else {
            System.out.println("else id=" + info);
            out.print("failed");
        }
    }

    if (key.equals("SerachingMedicalReport")) {
        JSONArray array = new JSONArray();
        System.out.println("Hello report search");
        String userkey = request.getParameter("userkey");
        String userid = request.getParameter("doctorid");
        String search = request.getParameter("searchdata");
//929c33c997f40e7af60c82f1
        String SEARCH = AES.encrypt(search, "secretKey");
        System.out.println("pname ---" + userkey);
        System.out.println("pname Encryption ---" + SEARCH);

        String qry = "SELECT `addmedical`.* FROM `addmedical`,`addpatient` WHERE `addmedical`.`pname`=`addpatient`.`name` AND `addmedical`.`uid`=`addpatient`.`userid` AND `addpatient`.`ukey`='"+search+"'";
        System.out.println("qry=" + qry);
        Iterator it = con.getData(qry).iterator();
        if (it.hasNext()){

            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("id", v.get(0).toString().trim());
                
                String Nam = AES.decrypt(v.get(1).toString(), "secretKey");
                obj.put("patientname", Nam.trim());

                String Co = AES.decrypt(v.get(3).toString(), "secretKey");
                obj.put("count", Co.trim());
                
                String BlO = AES.decrypt(v.get(2).toString(), "secretKey");
                obj.put("blood", BlO.trim());
                
                String Link = AES.decrypt(v.get(5).toString(), "secretKey");
                obj.put("link", Link.trim());
                
                //String IMG = AES.decrypt(v.get(6).toString(), "secretKey");
                obj.put("image", v.get(6).toString().trim());
                String CHO = AES.decrypt(v.get(4).toString(), "secretKey");

                obj.put("choles", CHO.trim());
                array.add(obj);
                
                System.out.println(array);
            }
            out.println(array);

        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

    //blockchain
    ArrayList<Block> blockchain = new ArrayList<Block>();
    int difficulty = 5;

    if (key.equals("Blockchain")) {
        String userid = request.getParameter("userid");
      String requestdata = request.getParameter("transacrequest");
       //    String requestdata ="929c33c997f40e7af60c82f1";

System.out.println("idd"+userid+"..."+requestdata);
        String qry = "SELECT distinct `addmedical`.`pname` FROM `addpatient`,`addmedical` WHERE `addpatient`.`userid`='"+userid+"' and addpatient.ukey='"+requestdata+"'";
        System.out.println("qry=" + qry);
        Iterator it = con.getData(qry).iterator();
        int i = 1;
        if (it.hasNext()) {
            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                System.out.println(v.get(0).toString());
                String h = String.valueOf(i);
                blockchain.add(new Block(v.get(0).toString(), v.get(0).toString()));
                System.out.println(" block" + i);
                blockchain.get(0).mineBlock(difficulty);
                i++;
            }
            
            System.out.println("\nBlockchain is Valid: " + NoobChain.isChainValid());
                      out.println("\nBlockchain is Valid: " + NoobChain.isChainValid());

            String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
                        Block k=new Block("","");

            out.println("Block chain Hash : " + k.hash);

            System.out.println("\nThe block chain: ");
            
            System.out.println(blockchainJson);
            out.println(blockchainJson);
                                  
        } else {
            out.print("failed");

        }
    }
        if (key.equals("userdatamedical")) {
        JSONArray array = new JSONArray();
        System.out.println("Hello report search");
        String userkey = request.getParameter("userkey");
       

      //  String SEARCH = AES.encrypt(search, "secretKey");

        System.out.println("pname ---" + userkey);

        String qry = "SELECT `addmedical`.* FROM `addmedical`,`addpatient` WHERE `addmedical`.`uid`=`addpatient`.`userid` AND `addmedical`.`pname`=`addpatient`.`name` and `addpatient`.`userid`='"+userkey+"'";
        System.out.println("qry=" + qry);
        Iterator it = con.getData(qry).iterator();
        if (it.hasNext()){

            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("id", v.get(0).toString().trim());
                
                String Nam = AES.decrypt(v.get(1).toString(), "secretKey");
                obj.put("patientname", Nam.trim());

                String Co = AES.decrypt(v.get(3).toString(), "secretKey");
                obj.put("count", Co.trim());
                
                String BlO = AES.decrypt(v.get(2).toString(), "secretKey");
                obj.put("blood", BlO.trim());
                
                String Link = AES.decrypt(v.get(5).toString(), "secretKey");
                obj.put("link", Link.trim());
                
                //String IMG = AES.decrypt(v.get(6).toString(), "secretKey");
                obj.put("image", v.get(6).toString().trim());
                String CHO = AES.decrypt(v.get(4).toString(), "secretKey");

                obj.put("choles", CHO.trim());
                array.add(obj);
                
                System.out.println(array);
            }
            out.println(array);

        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

%>

