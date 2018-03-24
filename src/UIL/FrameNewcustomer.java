/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIL;

import BLL.FaceDetector;
import BLL.PreProcess;
import BLL.Util;
import DBL.Customers;
import DBL.CustomersDB;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;

/**
 *
 * @author User
 */
public class FrameNewcustomer extends javax.swing.JFrame {

     private static final Logger logger = Logger.getLogger(FrameNewcustomer.class.getName());
     FileHandler fh;
    //definitions for image capture
    FrameGrabber grabber;
    IplImage ipimg;
    OpenCVFrameConverter.ToIplImage converter=new OpenCVFrameConverter.ToIplImage();
    BufferedImage bImg,captured,result;
    BufferedImage histImg,custImage[];
    static int i,uid,noi;
    
    class captureImage implements Runnable{
        protected volatile boolean runn = false;
        protected volatile FrameGrabber gr;
        Util uobj=new Util();
        @Override
        public void run() {
            try
            {
                grabber=new VideoInputFrameGrabber(0);
                grabber.start();
                while(runn)
                {
                    Frame frame=grabber.grab();
                    ipimg=converter.convertToIplImage(frame);
                    if(ipimg!=null)
                    {
                        //cvFlip(ipimg, ipimg, 1);
                        bImg=uobj.ipltoBuffered(ipimg);
                        Graphics g=jPanel1.getGraphics();
                        if (g.drawImage(bImg, 0, 0, getWidth(), getHeight() -150 , 0, 0, bImg.getWidth(), bImg.getHeight(), null))
                        if(runn == false)
                        {
                            System.out.println("Going to wait()");
			    this.wait();
                        }
                    }
                }
            }
            catch(Exception e)
            {
                 e.printStackTrace();
            }
        }
        
    }
    /**
     * Creates new form videoFrame
     */
    public FrameNewcustomer() {//constructor    
        initComponents();
        openWebcam();
        btnAdd.setEnabled(false);
        i=0;//for adding to custImage
        noi=0;
        custImage=new BufferedImage[3];//the image array
        txtAccountNumber.setEditable(false);
        uid=newUserid();
        generateAccount();
         try
        {
            fh = new FileHandler(".\\Logger.log", true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter); 
        }
        catch(IOException e)
        {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    private void openWebcam()//method to show the webcam from the runnable class
    {
        captureImage capt=new captureImage();
        Thread t=new Thread(capt);
        t.setDaemon(true);
        capt.runn=true;
        t.start();
    }
    
    private int newUserid()//gets user id for the new user
    {
        ResultSet rs=null;
        int tempid=0,usid=0;
        try
        {
            CustomersDB udobj=new CustomersDB();
            rs=udobj.getAlldetails();
            if(rs.next() == false)
            {
                System.out.println("Empty");
                usid=1;
            }
            else
            {
                do
                {
                    tempid=rs.getInt(1);
                }
                while(rs.next());
            }
            usid=tempid+1;
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return usid;
    }
    
    private void writeId(int id)//method to write the id
    {
        try
        {
            String text=String.valueOf(id)+",";
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(".\\trainingset\\traininglabels.txt", true)));
            pw.print(text);
            pw.close();
        }
        catch(IOException e)
        {
           logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    private void generateAccount()//generate account number
    {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String acc=date.replace("-","");
        String num=String.valueOf(uid);
        txtAccountNumber.setText(acc+num);
        txtAccountNumber.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCapture = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtMobile = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtAccountNumber = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        labelCaptured = new javax.swing.JLabel();
        labelNoimages = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        labelCapturedHist = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnCapture.setText("CAPTURE");
        btnCapture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaptureActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("CREATE NEW USER");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer details"));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Name");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Address");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Mobile");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Account Number");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(21, 21, 21)
                        .addComponent(txtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtAccountNumber))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(txtAccountNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnAdd.setText("ADD");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        labelCaptured.setText("Gray Image");

        labelNoimages.setText("No.of Images - ");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setPreferredSize(new java.awt.Dimension(320, 240));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 448, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        labelCapturedHist.setText("Equalized Image");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnCapture, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelCaptured, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelCapturedHist, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelNoimages, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(356, 356, 356)
                        .addComponent(jLabel2)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(labelNoimages))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnCapture, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelCaptured, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelCapturedHist, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCaptureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaptureActionPerformed

        FaceDetector fobj=new FaceDetector();
        PreProcess pobj=new PreProcess();
        try 
        {
            captured=bImg;
            result=fobj.detectFace(captured);
            histImg=pobj.histogramEqualization(result);
            if(histImg!=null)
            {
                labelCaptured.setIcon(new ImageIcon(result));
                labelCapturedHist.setIcon(new ImageIcon(histImg));
                btnAdd.setEnabled(true);
            //File out=new File("F:\\trial\\preProcess\\123Cature.jpg");
            //ImageIO.write(hist,"jpg", out);
            }
            else
            {
                btnAdd.setEnabled(false);
                JOptionPane.showMessageDialog(rootPane,"Try again please","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (HeadlessException e) 
        {
            logger.log(Level.SEVERE, e.getMessage(), e);
            JOptionPane.showMessageDialog(rootPane,"Error capturing "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCaptureActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
        if(txtName.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(rootPane,"Enter Name of the customer","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        else if(txtAddress.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(rootPane,"Enter Customer Address","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        else if(txtMobile.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(rootPane,"Enter valid mobile no","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        else if(txtMobile.getText().length()!=10)
        {
            JOptionPane.showMessageDialog(rootPane,"Enter valid mobile no","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        else if(noi!=8)
        {
            JOptionPane.showMessageDialog(rootPane,"Capture images and add","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            Customers uobj=new Customers();
            try
            {
                CustomersDB duobj=new CustomersDB();
                uobj.setName(txtName.getText());
                uobj.setAddress(txtAddress.getText());
                uobj.setMobile(Integer.parseInt(txtMobile.getText()));       
                uobj.setAccount(Integer.parseInt(txtAccountNumber.getText()));
                String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                uobj.setDate(date);
                int row=duobj.addCustomers(uobj);
                int row2=duobj.addAccountdet(uobj);
                if(row>0&&row2>0)
                {
                    JOptionPane.showMessageDialog(rootPane,"Created successfully","Created user",JOptionPane.INFORMATION_MESSAGE);
                    FrameAdministrator aobj=new FrameAdministrator();
                    aobj.show();     
                    this.dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(rootPane,"Could not create user","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
            catch(NumberFormatException | HeadlessException e)
            {
                logger.log(Level.SEVERE, e.getMessage(), e);
                JOptionPane.showMessageDialog(rootPane,"Could not create user"+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        
        noi=noi+1;//count of images
        try
        {
            if(noi<9)
            {
                
                File output=new File(".\\trainingset\\"+uid+"_"+noi+".jpg");
                ImageIO.write(histImg,"jpg",output);
                writeId(uid);//method to write the labels
                labelNoimages.setText("No.of Images - "+noi);
                if(noi==8)
                    JOptionPane.showMessageDialog(rootPane,"Training images Added","Added",JOptionPane.INFORMATION_MESSAGE);
                //i++;
            }
            else
                JOptionPane.showMessageDialog(rootPane,"You have added the images","Added",JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException | HeadlessException e)
        {
            logger.log(Level.SEVERE, e.getMessage(), e);
            JOptionPane.showMessageDialog(rootPane,"Could not add "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameNewcustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameNewcustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameNewcustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameNewcustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameNewcustomer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCapture;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelCaptured;
    private javax.swing.JLabel labelCapturedHist;
    private javax.swing.JLabel labelNoimages;
    private javax.swing.JTextField txtAccountNumber;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtMobile;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
