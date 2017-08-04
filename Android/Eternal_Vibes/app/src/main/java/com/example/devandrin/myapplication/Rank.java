public class Rank
{
	private String rank;
	private int poi
		rank = "Beginner";
		points = 500
	}
	
	public Rank(String rank, int points);
	{
		this.rank = rank;
		this.points = points;
	}
	
	public getRank()
	{
		return rank;
	}
	
	public void IncPoints(String activity)
	{
		if(activity == "Like")
		{
			points += 50;
		}
		if(activity == "Post")
		{
			points += 25;
		}
		if(activity == "Invited")
		{
			points += 20;
		}
		if(activity == "Attended Event")
		{
			points += 200;
		}
		UpdateRank();
	}
	
	private void  UpdateRank()
	{
		if(points < 5000)
		{
			rank = "Beginner";
		}
		if ((points <= 5000) && (points > 30000))
		{
			rank = "Intermediate";
		}
		if ((points <= 30000) && (points > 250000))
		{
			rank = "Expert";
		}
		if (points <= 250000) 
		{
			rank = "Master";
		}
	}
	
}