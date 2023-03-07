package com.dnspro.javatest.Services;

import java.util.ArrayList;
import java.util.HashSet;
// import java.io.BufferedReader;
// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.net.HttpURLConnection;
// import java.net.URL;
import java.util.List;
import java.util.Set;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.dnspro.javatest.DTO.JobResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class APIServices {

    private String urlApi="http://dev3.dansmultipro.co.id/api/recruitment/positions";


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
        
        String url2 = urlApi+".json"; // URL endpoint yang akan dipanggil
        WebClient webClient = WebClient.create();
        List<JobResponse> test = webClient.get()
                .uri(url2)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<JobResponse>>() {})
                .block();
        List<JobResponse> groupedByLocation= new ArrayList<>();
        /**
         * - baca location
         * - create temp pool per new unique location
         *      - create pool baru kalo location baru
         *      - insert to pool untuk location existing
         * - concat list?
         */
        Set<String> tempIndexLocation = new HashSet<>();
        Set<String> tempIndexId = new HashSet<>();
        for (JobResponse jb : test) {
            tempIndexLocation.add(jb.getLocation());
            System.out.println(jb.getLocation());
            System.out.println(jb.getId());
        }
        // brute force find and concat
        for (String location : tempIndexLocation) {
            System.out.println("Ambil job detail per location: " + location);
            for (JobResponse jb : test) {
                // if ( !tempIndexId.contains(jb.getId()) ) {
                    System.out.println(jb.getId()+" Location is : "+jb.getLocation());
                    if (jb.getLocation().equalsIgnoreCase(location)) {
                        System.out.println("location is same, get the job detail");
                        groupedByLocation.add(jb);
                        // tempIndexId.add(jb.getId());
                        System.out.println(jb.getId());
                    }
                // }
            }
        }
        return groupedByLocation;
    }

    public JobResponse getRequestJobDetail(String id) {
        String url2 = urlApi+"/"+id; // URL endpoint yang akan dipanggil
        WebClient webClient = WebClient.create();
        JobResponse test = webClient.get()
                .uri(url2)
                .retrieve()
                .bodyToMono(JobResponse.class)
                .block();
        return test;
    }
    
}
