package com.example.devandrin.myapplication;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Skillset
{
	private static final String url = "https://eternalvibes.me/Getskills/";


	private ArrayList<String> skills;
	
	public Skillset()
	{
		JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 try
                {
                    JSONObject o;

                    for(int i =0; i< response.length(); i++)
                    {
                        o = response.getJSONObject(i);
                        skills.add(o.getString("name"));
                    }
                    
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
		
		RequestQueueSingleton.getInstance(null).addToQ(jar);
	}

	public ArrayList<String> getSkillset()
	{
		return skills;
	}
	
	public String getSkill(int id)
	{
		return skills.get(id);
	}
	
	public int getID(String skill)
	{
		int id = 0;
		for(int i = 0; i > skills.size();i++)
		{
			if(skill == skills.get(i))
			{
				id = i;
			}
		}
		return id;
	}
	
	
	
	/*{
		Songwriter(1),
		Mixer(2),
		Producer(3),
		Singer(4),
		Rapper(5),
		Vocalist(6),
		Backup_Vocalist(7),
		Instrument(8),
		EventManagement(23);
		
		int id;
		
		Skills(int id)
		{
			this.id = id;
		}
		
		public int getID()
		{
			return id;
		}

	}
	
	private enum Instruments // Enum to store the available instrument skills
	{
		Guitar(9),
		Drums(10),
		Saxophone(11),
		Flute(12),
		Violin(13),
		Trumpet(14),
		Trombone(15),
		Tuba(16),
		French_Horn(17),
		Piano(18),
		Bells(19),
		Triangle(20),
		Xylophone(21),
		Harp(22);
		
		int id;
		
		Instruments(int id)
		{
			this.id = id;
		}
		
		public int getID()
		{
			return id;
		}
	}
	
	private Skills skills[];
	private Instruments instruments[];
	
	public Skillset(Skills skills[])
	{
		this.skills = skills;
		
	}

	
	public void setSkills(Skills[] skills)
	{
		this.skills = skills;
	}*/
}