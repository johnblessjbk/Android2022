package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.google.gson.GsonBuilder;
import PackChain.NoobChain;
import PackChain.Block;
import com.example.blockchain.CODE_ALGORITHM.AES;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import Connection.dbconnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.Iterator;

public final class BlockchainController_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

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
     //  String requestdata = request.getParameter("transacrequest");
           String requestdata ="b5ccbeda9f0f24b196208c3c";

System.out.println("idd"+userid+"..."+requestdata);
        String qry = "SELECT `addmedical`.`pname` FROM `addpatient`,`addmedical` WHERE `addpatient`.`userid`='"+userid+"' and addpatient.ukey='"+requestdata+"'";
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


      out.write('\n');
      out.write('\n');
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
