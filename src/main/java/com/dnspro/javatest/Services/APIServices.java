package com.dnspro.javatest.Services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.dnspro.javatest.DTO.JobResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class APIServices {


    public List<JobResponse> getRequestJobList() {
        /*
         * try {
            URL url = new URL("http://dev3.dansmultipro.co.id/api/recruitment/positions.json"); // URL endpoint yang akan dipanggil
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET"); // set metode request
            int status = con.getResponseCode(); // dapatkan status response
            if (status==200) {
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream())); // membaca response body
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect(); // tutup koneksi
    
                System.out.println(content.toString()); // tampilkan response body
                
               
            }
        }
        
        catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
         */
        
        String url2 = "http://dev3.dansmultipro.co.id/api/recruitment/positions.json"; // URL endpoint yang akan dipanggil
        WebClient webClient = WebClient.create();
        List<JobResponse> test = webClient.get()
                .uri(url2)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<JobResponse>>() {})
                .block();
        return test;
    }

    public JobResponse getRequestJobDetail(String id) {
        String url2 = "http://dev3.dansmultipro.co.id/api/recruitment/positions/"+id; // URL endpoint yang akan dipanggil
        WebClient webClient = WebClient.create();
        JobResponse test = webClient.get()
                .uri(url2)
                .retrieve()
                .bodyToMono(JobResponse.class)
                .block();
        return test;
    }
    
}
