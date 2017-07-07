package midgardsystem.com.wintowinner.dateSource;

/**
 * Created by Gabriel on 13/08/2016.
 *
 compile 'com.github.bumptech.glide:glide:3.6.0'
 */

public class RequestJSON {
    public static String URL_GET_ALL_SPEAKERS = "http://www.calidadeinocuidad.com/services/ccia/Services.php?opt=2";

    private void getSpeaker(int idSpeaker){
        /*
        RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());

        String url= AppConfig.URL_GET_SPEAKER+idSpeaker;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
               new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                 // Do something with the response
                 try{

                  JSONObject jObj = new JSONObject(response);
                  boolean success = jObj.getBoolean("success");
                  JSONArray values= jObj.getJSONArray("result");

                  if (success) {
                   for ( int i=0; i< values.length(); i++) {
                    JSONObject obj = values.getJSONObject(i);
                    objSpeaker.setId(obj.getInt("id"));

                    objSpeaker.setName(obj.getString("name"));
                    objSpeaker.setPosition(obj.getString("position"));
                    objSpeaker.setBiography(obj.getString("biography"));
                    //objSpeaker.setThumbnailUrl(obj.getString("image"));
                    objSpeaker.setPhrases(obj.getString("phrase"));
                    objSpeaker.setCompany(obj.getInt("fk_company"));


                    String urlImage= obj.getString("image").replace("'\'", "");
                    objSpeaker.setThumbnailUrl(urlImage);


                    speaker.setVisibility(View.VISIBLE);
                    thumbnailSpeaker.setImageUrl(objSpeaker.getThumbnailUrl(), imageLoader);
                    txtName.setText(objSpeaker.getName());
                    txtPosition.setText(objSpeaker.getPosition());
                    if(!(objSpeaker.getPhrases().equals(""))){
                     txtPhrases.setText("' " + objSpeaker.getPhrases() + " '");
                    }

                    RelativeLayout btnTopics = (RelativeLayout) rootView.findViewById(R.id.btnTopics);
                    btnTopics.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                      showTopicDialog(objSpeaker.getId());
                     }
                    });
                   }

                  }else{
                   String errorMsg = jObj.getString("msg");
                   //encodeMessage(errorMsg);
                  }

                 }  catch (JSONException ex){
                  Toast.makeText(getContext(), "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                 }

                }
               },
               new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError ex) {
                 // Handle error
                 Toast.makeText(getContext(), "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                }
               });

       rq.add(stringRequest );
*/
      }
}
