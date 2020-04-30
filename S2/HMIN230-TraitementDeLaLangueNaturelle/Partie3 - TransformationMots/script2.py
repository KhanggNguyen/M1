from lxml.html import etree
# importing the requests library 
import requests 

import os,sys,re

word = input("Veuillez saisir un terme : " ).lower()

# defining the api-endpoint  
URL = "http://www.jeuxdemots.org/rezo-dump.php"

PARAMS = {
    'gotermsubmit' : 'Chercher',
    'gotermrel' : word,
    'rel' : 4
} 

# sending get request and saving the response as response object 
r = requests.get(url = URL, params = PARAMS)

# extracting data in json format 
data = etree.HTML(r.text)

print(data.xpath('//code')[0].text)

