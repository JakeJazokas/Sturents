from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver import Chrome
import re
import json


chrome_options = Options()
#Headless chrome
#chrome_options.add_argument("--headless")
driver = webdriver.Chrome(options=chrome_options)
#Go to Kijiji
start_url = "https://www.kijiji.ca/b-for-rent/ottawa/student/k0c30349001l1700185?ad=offering"
driver.get(start_url)

#Function to get titles and links
def get_titles_and_links(links):
    titles_and_links = [{'title':link.text, "url":link.get_attribute('href')} for link in links if link.get_attribute('href') != None]
    return titles_and_links

#Page 1
links = driver.find_elements_by_class_name('title')
all_found_listings = get_titles_and_links(links)

#Pages 2->num
for i in range(2,3):
    goto_url = "https://www.kijiji.ca/b-for-rent/ottawa/student/page-{0}/k0c30349001l1700185?ad=offering".format(i)
    driver.get(goto_url)
    all_found_listings = all_found_listings + get_titles_and_links(driver.find_elements_by_class_name('title'))

#print(all_found_listings)
#Output the listings to listings.json (formatted nicely)
with open('listings.json', 'w') as json_file:
   json.dump(all_found_listings, json_file, indent=4, sort_keys=True)

#Exit
driver.quit()
