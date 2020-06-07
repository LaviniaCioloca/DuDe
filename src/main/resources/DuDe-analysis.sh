currentPercentage=`grep -o '<td>.*%</td>' dude-statistics.html | awk -F'[>|%]' '{print $2}' | head -1`

previousPercentage=`grep -o '<td>.*%</td>' dude-statistics.html | awk -F'[>|%]' '{print $2}' | tail -1`

percentageChange=`echo $currentPercentage - $previousPercentage | bc -l`

printf "\n--- DuDeJenkinsPlugin result ---\n"
if (( $(echo "$percentageChange > $maximum_duplication_percentage_increase_allowed" | bc -l) )) && (( $(echo "$currentPercentage > $maximum_duplication_percentage_in_project" | bc -l) ));
then
  {
    printf "ERROR! Duplication percentage increase %s is greater than %s and the current duplication percentage %s exceeds the threshold value %s \n\n"
    "$percentageChange" "$maximum_duplication_percentage_increase_allowed" "$currentPercentage" "$maximum_duplication_percentage_in_project" ;
    exit 1;
  }
elif (( $(echo "$percentageChange > $maximum_duplication_percentage_increase_allowed" | bc -l) ));
then
  {
    printf "ERROR! Duplication percentage increase %s is greater than %s \n\n" ;
    "$percentageChange" "$maximum_duplication_percentage_increase_allowed"
    exit 1;
  }
elif (( $(echo "$currentPercentage > $maximum_duplication_percentage_in_project" | bc -l) ));
then
  {
    printf "ERROR! The current duplication percentage %s exceeds the threshold value %s \n\n" ;
    "$currentPercentage" "$maximum_duplication_percentage_in_project"
    exit 1;
  }
else
  {
    printf "SUCCESS! Duplication in project is lower than the threshold values \n\n" ;
    exit 0;
  }
fi

rm -f DuDe-script.sh
