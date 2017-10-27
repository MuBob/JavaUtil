package http;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;


public class CertificateChainDemo {

    public String caName = "caroot";
    public String caPasswd = "******";

    public String keyStorePasswd = "******";

    public String keyStorePath = "/root/.keystore";

    public String userDN = "CN=loong,   OU=CS,   O=HUST ,L=Wuhan, ST=Hubei, C=CN";
    public String userAlias = "loong";    // 用户别名


    public CertificateChainDemo() {
    }


    public void listX509CertificateInfo(String certFile) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate x509Cert = (X509Certificate) cf.generateCertificate(new FileInputStream(certFile));
            System.out.println("\nIssuerDN:" + x509Cert.getIssuerDN());
            System.out.println("Signature alg:" + x509Cert.getSigAlgName());
            System.out.println("Version:" + x509Cert.getVersion());
            System.out.println("Serial Number:" + x509Cert.getSerialNumber());
            System.out.println("Subject DN:" + x509Cert.getSubjectDN());
            System.out.println("Public Kye:" + x509Cert.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean Verify(String certPath) {
        Certificate cert;
        PublicKey caPublicKey;

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            FileInputStream in = new FileInputStream(certPath);
            cert = cf.generateCertificate(in);
            in.close();
            X509Certificate t = (X509Certificate) cert;
            Date timeNow = new Date();
            t.checkValidity(timeNow);

            in = new FileInputStream(keyStorePath);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, keyStorePasswd.toCharArray());
            in.close();
            caPublicKey = ks.getCertificate(caName).getPublicKey();
            System.out.println("\nCA public key:\n" + caPublicKey);
            try {
                cert.verify(caPublicKey);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("no pass.\n");
                e.printStackTrace();
            }
            System.out.println("\npass.\n");
        } catch (CertificateExpiredException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (CertificateNotYetValidException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (CertificateException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (KeyStoreException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return true;
    }

    public static void main(String args[]) {
        String userCertPath = "E:\\JavaProject\\Util\\NetRequest\\src\\context\\api.im.crt";
        CertificateChainDemo ccd = new CertificateChainDemo();
        ccd.listX509CertificateInfo(userCertPath);
//        ccd.Verify(userCertPath);
    }
}

