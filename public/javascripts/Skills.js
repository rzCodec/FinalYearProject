var Skills =
    {
        skills: getSkills(),
        getSkills: function ()
        {
            $.ajax(
                {
                    type: "GET",
                    url: "https://eternalvibes.me/getSkills",
                    cache: false,
                    dataType: "jsonp",
                    success: function (data)
                    {
                        console.log(data);
                        skills = data;
                    },
                    error: function (data)
                    {
                        console.log("error");
                        skills = data;
                    }
                }).then(function()
            {
                return skills;
            });
        },

        addSkill: function(skill)
        {
            console.log('this skill' + skill);
            if (skill == '')
            {
                materialize.toast("please enter a skill", 5000);
            }
            else {
                $.post("https://www.eternalvibes.me/addSkill",
                    {
                        skillName: skill
                    },
                    function (data)
                    {
                        console.log("Data: " + data);
                        if (data == 200)
                        {


                        }
                    });
                materialize.toast(skill + " has been added", 500);

            }
        },

        getSkill: function(id)
        {
            skills.forEach( function(skill)
            {
                if(skill.id == id)
                {
                    return skill;
                }
            });
        }

    }





