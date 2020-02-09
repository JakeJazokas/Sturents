from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver import Chrome
import re
import json
import time

chrome_options = Options()
#Headless chrome
#chrome_options.add_argument("--headless")
driver = webdriver.Chrome(options=chrome_options)
#Go to Kijiji
start_url = "https://www.kijiji.ca/b-for-rent/ottawa/student/k0c30349001l1700185?ad=offering"
driver.get(start_url)

#Time to wait in between each page loading
driver.set_page_load_timeout(30)

#Function to get the description of a given url
def get_listing_description(link):
    #10 second sleep to avoid bot detection
    time.sleep(10)
    #Go to the URL and get the description text
    driver.get(link)
    description_text = driver.find_element_by_class_name('descriptionContainer-3544745383').text
    return description_text

#Function to get titles and links
def get_titles_and_links(links):
    titles_and_links = [{'title':link.text, 'url':link.get_attribute('href'), 'description':'N/A'} for link in links if link.get_attribute('href') != None]
    #After we have all the links for the page, update all the descriptions
    for listing in titles_and_links:
        listing['description'] = get_listing_description(listing['url'])
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
