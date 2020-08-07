// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.io.IOException;
import com.google.gson.Gson;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;




/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {


  private ArrayList<String> messages = new ArrayList<String>();

  static class Data{
     Long id;
     String comment;
     long timestamp;

     /**Constructor*/
      public Data(long id, String comment, long timestamp) { 
        this.id = id; 
        this.comment= comment; 
        this.timestamp= timestamp;
    } 
  }
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String commentString = request.getParameter("data-input");
      //System.out.println(commentString);
      long timestamp = System.currentTimeMillis();
      //System.out.println(timestamp);
      messages.add(commentString);
      //System.out.println(messages);
      String maxstring = request.getParameter("comment-limit");
      
      //Create entity 
      Entity dataEntity = new Entity("Data");
      dataEntity.setProperty("comment", commentString);
      dataEntity.setProperty("timestamp", timestamp);
   
      //Create datastore and put entity
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      if (commentString!=null && !commentString.isEmpty()) {datastore.put(dataEntity);}
      

      response.sendRedirect("index.html?comment-limit=" + maxstring);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    //String json = convertToJson(messages);
    Query query = new Query("Data").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    List<Data> comments = new ArrayList<>();
    int no = 0; 
    int max= getLimitChoice(request);
    //System.out.println(max);
    for (Entity entity : results.asIterable()) {
      no++;
      long id = entity.getKey().getId();
      String title = (String) entity.getProperty("comment");
      long timestamp = (long) entity.getProperty("timestamp");

      Data comment = new Data(id,title,timestamp);
      //System.out.println(comment);
      comments.add(comment);
      if (no == max) break;
    }
    //System.out.println(comments);
    Gson gson = new Gson();

    response.setContentType("application/json");
    response.getWriter().println(gson.toJson(comments));
  }
   
    private int getLimitChoice(HttpServletRequest request) {
    // Get the input from the form.
    String maxstring = request.getParameter("comment-limit");
    System.out.println("Max string: " + maxstring);
    // Convert the input to an int.
    int max;
    try{
      max = Integer.parseInt(maxstring);
    } catch (NumberFormatException e) {
        System.out.println("Exception: " + e.getMessage());
      return 10;
    }
    return max;
    }
}
 