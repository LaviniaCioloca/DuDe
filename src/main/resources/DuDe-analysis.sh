# Clean the workspace by deleting the previous script than ran DuDe
rm -f DuDe-script.sh

# Calculate duplication percentages based on the HTML statistic report generated by DuDe
currentPercentage=$(grep -o '<td>.*%</td>' dude-statistics.html | awk -F'[>|%]' '{print $2}' | head -1)

previousPercentage=$(grep -o '<td>.*%</td>' dude-statistics.html | awk -F'[>|%]' '{print $2}' | tail -1)

# If there is a N/A value for the previous duplication percentage, do not consider any percentage change
if [ "$previousPercentage" = "N/A" ];
then
  percentageChange=0
else
  percentageChange=$(echo "$currentPercentage" - "$previousPercentage" | bc -l)
fi


# Determine the duplication impact and the result of the build
printf "\n--- DuDeJenkinsPlugin result ---\n"
if (( $(echo "$percentageChange > $maximum_duplication_percentage_increase_allowed" | bc -l) )) && (( $(echo "$currentPercentage > $maximum_duplication_percentage_in_project" | bc -l) ));
then
  {
    printf "ERROR! Duplication percentage increase %s is greater than %s and the current duplication percentage %s exceeds the threshold value %s \n\n" "$percentageChange" "$maximum_duplication_percentage_increase_allowed" "$currentPercentage" "$maximum_duplication_percentage_in_project" ;
    exit 1;
  }
elif (( $(echo "$percentageChange > $maximum_duplication_percentage_increase_allowed" | bc -l) ));
then
  {
    printf "ERROR! Duplication percentage increase %s is greater than the threshold value %s \n\n" "$percentageChange" "$maximum_duplication_percentage_increase_allowed" ;
    exit 1;
  }
elif (( $(echo "$currentPercentage > $maximum_duplication_percentage_in_project" | bc -l) ));
then
  {
    printf "ERROR! The current duplication percentage %s exceeds the threshold value %s \n\n" "$currentPercentage" "$maximum_duplication_percentage_in_project" ;
    exit 1;
  }
else
  {
    printf "SUCCESS! Duplication in project is lower than the threshold values \n\n" ;
    exit 0;
  }
fi
