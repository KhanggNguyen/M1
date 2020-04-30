#Participants : 
#NGUYEN Huu Khang - 21506865 

#TRAN Thi Tra My - 21511002

# importing the requests library 
import requests 
import os,sys,re

word = input("Veuillez saisir un terme : " ).lower()

#open file data r_pos
file_r_pos = open('Dataset/r_pos.txt', encoding="latin1")
lines = file_r_pos.readlines()

#open file rule
file_rule = open('Dataset/regles.txt', 'a', encoding="utf-8")
file_rule_2 = open('Dataset/regles_2.txt', 'a', encoding="utf-8")

#Type of input word via r_pos
wordTypes = []
for line in lines :
    lineToArray = line.split(';')
    if(word == lineToArray[0].strip() and int(lineToArray[2]) > 0):
        wordTypes.append(line)
        file_rule.write(line)

# defining the URL  
URL = "http://www.jeuxdemots.org/rezo-dump.php"

########################
#Verify if word exists and its form
def checkIfWordExist(word) :
    exist = False
    
    # defining params
    PARAMS = {
        'gotermsubmit' : 'Chercher',
        'gotermrel' : word,
        'rel' : 4
    }

    # sending get request and saving the response as response object 
    r = requests.get(url = URL, params = PARAMS)

    # extracting data in text format 
    data = r.text

    if (data.find(';\'' + word + '\';') > 0):
        for line in lines :
            exist = True
            lineToArray = line.split(';')

            if((word == lineToArray[0].strip()) and (int(lineToArray[2]) > 0)):
                file_rule.write(line)
                exist = True
    return exist

#return True if word is a Ver:Inf
def isVer_Inf(word) : 
    for line in wordTypes : 
        lineToArray = line.split(';')
        form = lineToArray[1]
        if(form == 'Ver:Inf') :
            return True
    return False

def isNom(word) : 
    for line in wordTypes : 
        lineToArray = line.split(';')
        form = lineToArray[1]
        if(form == 'Nom:') :
            return True
    return False

def isADJ(word) : 
    for line in wordTypes : 
        lineToArray = line.split(';')
        form = lineToArray[1]
        if(form =='Adj:') :
            return True
    return False

def isADV(word) : 
    for line in wordTypes : 
        lineToArray = line.split(';')
        form = lineToArray[1]
        if(form == 'Adv:') :
            return True
    return False

#Write to rule file
def __writeToRuleFile (word_origine, word_derived, motif_found, motif_replaced, semantique_origine, semantique_derived ) :
    file_rule_2.write(word_origine + ';' + word_derived + ';' + motif_found + ';' + motif_replaced + ';' + semantique_origine + '=>' + semantique_derived + '\n')

##################################################################

#Generate new word from type Ver
def transformVer_Inf(word) :
    
    #transform to list character
    myListWord = list(word)
    lenOfList = len(myListWord)
    
    #if ER
    if (myListWord[lenOfList-2] == "e" and myListWord[lenOfList-1] == "r" ) :
        #REMOVE ER
        myListWord.pop()
        myListWord.pop()
        #ER -> AGE
        if(checkIfWordExist(''.join(__generateWordAGE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordAGE(myListWord)), 'ER', 'AGE', 'Ver', 'Nom')
            print(''.join(__generateWordAGE(myListWord)))

        #ER -> ABLE 
        if(checkIfWordExist(''.join(__generateWordABLE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordABLE(myListWord)), 'ER', 'AGE', 'Ver', 'Nom')
            print(''.join(__generateWordABLE(myListWord)))

        #ER -> EMENT
        if(checkIfWordExist(''.join(__generateWordEMENT(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordEMENT(myListWord)), 'ER', 'AGE', 'Ver', 'Nom')
            print(''.join(__generateWordEMENT(myListWord)))
        
        #ER -> EUR
        if(checkIfWordExist(''.join(__generateWordEUR(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordEUR(myListWord)), 'ER', 'EUR', 'Ver', 'Adj')
            print(''.join(__generateWordEUR(myListWord)))

        #ER -> EUSE
        if(checkIfWordExist(''.join(__generateWordEUSE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordEUSE(myListWord)), 'ER', 'EUSE', 'Ver', 'Adj')
            print(''.join(__generateWordEUSE(myListWord)))
        
        #ER -> OIR (egoutter -> egouttoir)
        if(checkIfWordExist(''.join(__generateWordOIR(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordOIR(myListWord)), 'ER', 'OIR', 'Ver', 'Nom')
            print(''.join(__generateWordOIR(myListWord)))

        #ER -> OIRE (patiner -> patinoire)
        if(checkIfWordExist(''.join(__generateWordOIRE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordOIRE(myListWord)), 'ER', 'OIRE', 'Ver', 'Nom')
            print(''.join(__generateWordOIRE(myListWord)))

        #ER -> ANT
        if(checkIfWordExist(''.join(__generateWordANT(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordANT(myListWord)), 'ER', 'ANT', 'Ver', 'Nom')
            print(''.join(__generateWordANT(myListWord)))
        
        #ER -> ETTE
        if(checkIfWordExist(''.join(__generateWordETTE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordETTE(myListWord)), 'ER', 'ETTE', 'Ver', 'Nom')
            print(''.join(__generateWordETTE(myListWord)))

        #ER -> E
        if(checkIfWordExist(''.join(__generateWordE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordE(myListWord)), 'ER', 'E', 'Ver', 'Ver')
            print(''.join(__generateWordE(myListWord)))

        #ER -> ATION
        if(checkIfWordExist(''.join(__generateWordATION(myListWord)))):
            __writeToRuleFile(word, ''.join(__generateWordATION(myListWord)), 'ER', 'ATION', 'Ver', 'Nom')
            print(''.join(__generateWordATION(myListWord)))
        
        #ER -> AISON
        if(checkIfWordExist(''.join(__generateWordAISON(myListWord)))):
            __writeToRuleFile(word, ''.join(__generateWordAISON(myListWord)), 'ER', 'AISON', 'Ver', 'Nom')
            print(''.join(__generateWordAISON(myListWord)))
        
        #ER -> ILLER
        if(checkIfWordExist(''.join(__generateWordILLER(myListWord)))):
            __writeToRuleFile(word, ''.join(__generateWordILLER(myListWord)), 'ER', 'ILLER', 'Ver', 'Nom')
            print(''.join(__generateWordILLER(myListWord)))

        #ER -> AILLE (retrouver -> retrouvaille)
        if(checkIfWordExist(''.join(__generateWordAILLE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordAILLE(myListWord)), 'ER', 'AILLE', 'Ver', 'Nom')
            print(''.join(__generateWordAILLE(myListWord)))

        #ER -> AIL 
        if(checkIfWordExist(''.join(__generateWordAIL(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordAIL(myListWord)), 'ER', 'AIL', 'Ver', 'Nom')
            print(''.join(__generateWordAIL(myListWord)))

        #ER -> URE (geler -> gelure)
        if(checkIfWordExist(''.join(__generateWordURE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordURE(myListWord)), 'ER', 'URE', 'Ver', 'Nom')
            print(''.join(__generateWordURE(myListWord)))

        #ER -> IEN
        if(checkIfWordExist(''.join(__generateWordIEN(myListWord)))):
            __writeToRuleFile(word, ''.join(__generateWordIEN(myListWord)), 'ER', 'IEN', 'Ver', 'Nom')
            print(''.join(__generateWordIEN(myListWord)))

        #ER -> IBLE
        if(checkIfWordExist(''.join(__generateWordIBLE(myListWord)))):
            __writeToRuleFile(word, ''.join(__generateWordIBLE(myListWord)), 'ER', 'IBLE', 'Ver', 'Nom')
            print(''.join(__generateWordIBLE(myListWord)))

    
    #IF SER
    if(myListWord[len(myListWord)-1] == 's') :   
        #SER -> SATION (Utiliser -> utilisation)
        if(checkIfWordExist(''.join(__generateWordSATION(myListWord)))):
            __writeToRuleFile(word, ''.join(__generateWordSATION(myListWord)), 'SER', 'SATION', 'Ver', 'Nom')
            print(''.join(__generateWordSATION(myListWord)))

    #if GER
    if(myListWord[len(myListWord)-1] == 'g') :
        #GER -> GEOIRE (manger -> mangeoire)
        if(checkIfWordExist(''.join(__generateWordEOIRE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordEOIRE(myListWord)), 'GER', 'EOIRE', 'Ver', 'Nom')
            print(''.join(__generateWordEOIRE(myListWord)))

        #GER -> GEABLE (manger -> mangeable)
        if(checkIfWordExist(''.join(__generateWordEABLE(myListWord)))) : 
            __writeToRuleFile(word, ''.join(__generateWordEABLE(myListWord)), 'GER', 'EABLE', 'Ver', 'Adj')
            print(''.join(__generateWordEABLE(myListWord)))
        
        #GER -> GEANT (manger -> mangeant)
        if(checkIfWordExist(''.join(__generateWordEANT(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordEANT(myListWord)), 'GER', 'EANT', 'Ver', 'Adv')
            print(''.join(__generateWordEANT(myListWord)))

    #IF NER
    if(myListWord[len(myListWord)-1] == 'n') :      
        #NER -> NIER (janidier -> jardinier)
        if(checkIfWordExist(''.join(__generateWordNIER(myListWord)))):
            __writeToRuleFile(word, ''.join(__generateWordNIER(myListWord)), 'NER', 'NIER', 'Ver', 'Nom')
            print(''.join(__generateWordNIER(myListWord)))

        #NER -> NAISON
        if(checkIfWordExist(''.join(__generateWordNAISION(myListWord)))):
            __writeToRuleFile(word, ''.join(__generateWordNAISION(myListWord)), 'NER', 'NAISON', 'Ver', 'Adv')
            print(''.join(__generateWordNAISION(myListWord)))

    #IF QUER
    if(myListWord[len(myListWord)-2] == "q" and myListWord[len(myListWord)-1] == "u") :
        
        #QUER -> CATION (fabriquer -> fabrication)
        if(checkIfWordExist(''.join(__generateWordCATION(myListWord)))):
            __writeToRuleFile(word, ''.join(__generateWordCATION(myListWord)), 'QUER', 'CATION', 'Ver', 'Nom')
            print(''.join(__generateWordCATION(myListWord)))

        #QUER -> CABLE 
        if(checkIfWordExist(''.join(__generateWordCABLE(myListWord)))):
            __writeToRuleFile(word, ''.join(__generateWordEANT(myListWord)), 'QUER', 'CABLE', 'Ver', 'Adj')
            print(''.join(__generateWordCABLE(myListWord)))

        #QUER -> CANT
        if(checkIfWordExist(''.join(__generateWordCANT(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordCANT(myListWord)), 'QUER', 'CANT', 'Ver', 'Adj')
            print(''.join(__generateWordCANT(myListWord)))

    #IF IR 
    myListWord = list(word)
    if (myListWord[len(myListWord)-2] == "i" and myListWord[len(myListWord)-1] == "r") : 
        myListWord.pop()

        #IR -> ISSERIE
        if(checkIfWordExist(''.join(__generateWordISSERIE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordISSERIE(myListWord)), 'IR', 'ISSERIE', 'Ver', 'Nom')
            print(''.join(__generateWordISSERIE(myListWord)))

        #IR -> ISSEMENT
        if(checkIfWordExist(''.join(__generateWordSSEMENT(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordSSEMENT(myListWord)), 'IR', 'ISSEMENT', 'Ver', 'Nom')
            print(''.join(__generateWordSSEMENT(myListWord)))

        #IR -> ISSEUR
        if(checkIfWordExist(''.join(__generateWordSSEUR(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordSSEUR(myListWord)), 'IR', 'ISSEUR', 'Ver', 'Nom')
            print(''.join(__generateWordSSEUR(myListWord)))

        #IR -> ISSEUSE
        if(checkIfWordExist(''.join(__generateWordSSEUSE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordSSEUSE(myListWord)), 'IR', 'ISSEUSE', 'Ver', 'Nom')
            print(''.join(__generateWordSSEUSE(myListWord)))

        #IR -> ISSANT
        if(checkIfWordExist(''.join(__generateWordSSANT(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordSSANT(myListWord)), 'IR', 'ISSANT', 'Ver', 'Nom')
            print(''.join(__generateWordSSANT(myListWord)))

        #IR -> ITION
        if(checkIfWordExist(''.join(__generateWordITION(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordITION(myListWord)), 'IR', 'ITION', 'Ver', 'Nom')
            print(''.join(__generateWordITION(myListWord)))

    #IF RE 
    myListWord = list(word)
    if(myListWord[len(myListWord)-2 == 'r'] 
        and myListWord[len(myListWord)-1 == 'e']) :

        ##remove RE
        myListWord.pop()
        myListWord.pop()

        #RE -> ENTIF
        if(checkIfWordExist(''.join(__generateWordENTIF(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordENTIF(myListWord)), 'RE', 'ENTIF', 'Ver', 'Adj')
            print(''.join(__generateWordENTIF(myListWord)))

    #IF DRE 
    myListWord = list(word)
    if(myListWord[len(myListWord)-3 == 'd'] 
        and myListWord[len(myListWord)-2 == 'r'] 
        and myListWord[len(myListWord)-1 == 'e']) :

        ##remove NDRE
        myListWord.pop()
        myListWord.pop()
        myListWord.pop()
        myListWord.pop()

        #DRE -> NABLE
        if(checkIfWordExist(''.join(__generateWordNABLE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordNABLE(myListWord)), 'DRE', 'NABLE', 'Ver', 'Adj')
            print(''.join(__generateWordNABLE(myListWord)))
          

    #IF UIRE
    myListWord = list(word)
    if(myListWord[len(myListWord)-4 == 'u'] 
        and myListWord[len(myListWord)-3 == 'i'] 
        and myListWord[len(myListWord)-2 == 'r'] 
        and myListWord[len(myListWord)-1 == 'e']) :
        
        ##remove UIRE
        myListWord.pop()
        myListWord.pop()
        myListWord.pop()
        myListWord.pop()

        #UIRE -> UCTION
        if(checkIfWordExist(''.join(__generateWordUCTION(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordUCTION(myListWord)), 'UIRE', 'UCTION', 'Ver', 'Nom')
            print(''.join(__generateWordUCTION(myListWord)))

        #UIRE -> UCTEUR
        if(checkIfWordExist(''.join(__generateWordUCTEUR(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordUCTEUR(myListWord)), 'UIRE', 'UCTEUR', 'Ver', 'Nom')
            print(''.join(__generateWordUCTEUR(myListWord)))

        #UIRE -> UCTRICE
        if(checkIfWordExist(''.join(__generateWordUCTRICE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordUCTRICE(myListWord)), 'UIRE', 'UCTRICE', 'Ver', 'Nom')
            print(''.join(__generateWordUCTRICE(myListWord)))


    #IF V-ISTER 
    myListWord = list(word)
    if(myListWord[len(myListWord)-5 == 'i'] 
        and myListWord[len(myListWord)-4 == 's'] 
        and myListWord[len(myListWord)-3 == 't'] 
        and myListWord[len(myListWord)-2 == 'e'] 
        and myListWord[len(myListWord)-1 == 'r']) :

        #ISTER -> ISTANCE
        if(checkIfWordExist(''.join(__generateWordISTANCE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordISTANCE(myListWord)), 'ISTER', 'ISTANCE', 'Ver', 'Nom')
            print(''.join(__generateWordISTANCE(myListWord)))

    #try add random suffixe
    myListWord = list(word)
    # * -> ATEUR
    if(checkIfWordExist(''.join(__generateWordATEUR(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordATEUR(myListWord)), '*', 'ATEUR', 'Ver', 'Nom')
        print(''.join(__generateWordATEUR(myListWord)))

    # * -> ITEUR
    if(checkIfWordExist(''.join(__generateWordITEUR(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordITEUR(myListWord)), '*', 'ITEUR', 'Ver', 'Nom')
        print(''.join(__generateWordITEUR(myListWord)))

#METHOD GENERATE VERB
#------------V-er ------------
#nom + action
def __generateWordAGE (listWord):
    myListWordTempo = listWord.copy()
    myListWordTempo.append('age')
    return myListWordTempo

#ER -> EMENT
def __generateWordEMENT (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ement')
    return myListWordTempo

#ER -> EUR
def __generateWordEUR (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('eur')
    return myListWordTempo

#ER -> EUSE
def __generateWordEUSE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('euse')
    return myListWordTempo

#ER -> OIR
def __generateWordOIR (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('oir')
    return myListWordTempo

#ER -> ABLE
def __generateWordABLE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('able')
    return myListWordTempo

#ER -> OIRE
def __generateWordOIRE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('oire')
    return myListWordTempo

#ER -> ILLER
def __generateWordILLER(listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('iller')
    return myListWordTempo

#ER -> AIL
def __generateWordAIL (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ail')
    return myListWordTempo

#ER -> ATION
def __generateWordATION (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ation')
    return myListWordTempo

#ER -> E
def __generateWordE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('e')
    return myListWordTempo

#ER -> URE
def __generateWordURE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ure')
    return myListWordTempo

#ER -> AILLE
def __generateWordAILLE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('aille')
    return myListWordTempo

#ER -> IEN 
def __generateWordIEN(listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ien')
    return myListWordTempo

#ER -> AISON 
def __generateWordAISON(listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('aison')
    return myListWordTempo

#ER -> IBLE 
def __generateWordIBLE(listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ible')
    return myListWordTempo

#ER -> ANT 
def __generateWordANT(listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ant')
    return myListWordTempo

#-------------V-RE
#RE -> ENTIF
def __generateWordENTIF(listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('entif')
    return myListWordTempo


#-------------V-SER-----------
#SER -> SATION
def __generateWordSATION (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ation')
    return myListWordTempo

#--------------V-GER-------------
def __generateWordEOIRE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('eoire')
    return myListWordTempo

def __generateWordETTE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ette')
    return myListWordTempo

def __generateWordEABLE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('eable')
    return myListWordTempo

def __generateWordEANT (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('eant')
    return myListWordTempo


#--------------V-QUER----------
def __generateWordCANT (listWord) :
    myListWordTempo = listWord.copy()
    #remove qu and replace with c
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.append("cant")
    return myListWordTempo

def __generateWordCABLE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.append('cable')
    return myListWordTempo

def __generateWordCATION (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.pop()
    myListWordTempo.append('cation')
    return myListWordTempo

#------------V-NER----------
#NER -> NIER
def __generateWordNIER (listWord) : 
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ier')
    return myListWordTempo

#NER -> NAISON
def __generateWordNAISION (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('aision')
    return myListWordTempo

#------------ V-IR ------------
#IR -> ISSERIE
def __generateWordISSERIE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('sserie')
    return myListWordTempo

#IR -> ISSEMENT
def __generateWordSSEMENT (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ssement')
    return myListWordTempo

#IR -> ISSEUR
def __generateWordSSEUR (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('sseur')
    return myListWordTempo

#IR -> ISSEUSE
def __generateWordSSEUSE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('sseuse')
    return myListWordTempo

#IR -> ISSANT
def __generateWordSSANT (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ssant')
    return myListWordTempo

#IR -> ITION
def __generateWordITION (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ition')
    return myListWordTempo


#IRE 


#-------------V - UIRE 
#UIRE -> UCTION
def __generateWordUCTION (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('uction')
    return myListWordTempo
    
#UIRE -> UCTEUR
def __generateWordUCTEUR (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ucteur')
    return myListWordTempo

#UIRE -> UCTRICE
def __generateWordUCTRICE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('uctrice')
    return myListWordTempo

#-----------V-ISTER
def __generateWordISTANCE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('istance')
    return myListWordTempo

#-----------V-DRE
def __generateWordNABLE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('able')
    return myListWordTempo

# * -> ATEUR
def __generateWordATEUR (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ateur')
    return myListWordTempo

# * -> iteur
def __generateWordITEUR (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('iteur')
    return myListWordTempo

###########################
###########################
###########################

#Generate new word from type : Nom
def transformNom(word):
    #transform to list character
    myListWord = list(word)
    lenOfList = len(myListWord)

    #IF E
    if (myListWord[lenOfList-1] == "e") :
        #E -> ON 
        if(checkIfWordExist(''.join(__generateWordON(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordON(myListWord)), 'E', 'On', 'Nom', 'Nom')
            print(''.join(__generateWordON(myListWord)))

        #E -> ER 
        if(checkIfWordExist(''.join(__generateWordER_1(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordAIRE(myListWord)), 'E', 'ER', 'Nom', 'Ver')
            print(''.join(__generateWordER_1(myListWord)))

        #E -> AIRE 
        if(checkIfWordExist(''.join(__generateWordAIRE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordAIRE(myListWord)), 'E', 'AIRE', 'Nom', 'Adj')
            print(''.join(__generateWordAIRE(myListWord)))

        #E -> IF 
        if(checkIfWordExist(''.join(__generateWordIF(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordIF(myListWord)), 'E', 'IF', 'Nom', 'Adj')
            print(''.join(__generateWordIF(myListWord)))

        #E -> EUX 
        if(checkIfWordExist(''.join(__generateWordEUX(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordEUX(myListWord)), 'E', 'EUX', 'Nom', 'Adj')
            print(''.join(__generateWordEUX(myListWord)))

        #E -> IE 
        if(checkIfWordExist(''.join(__generateWordIE_BIS(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordIE_BIS(myListWord)), 'E', 'IE', 'Nom', 'Nom')
            print(''.join(__generateWordIE_BIS(myListWord)))

    #IF ER
    if (myListWord[lenOfList-2] == "e" and myListWord[lenOfList-1] == "r") :
        # ER -> ANCE
        if(checkIfWordExist(''.join(__generateWordANCE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordANCE(myListWord)), 'ER', 'ANCE', 'Nom', 'Nom')
            print(''.join(__generateWordANCE(myListWord)))

    #IF EMENT
    if (myListWord[lenOfList-5] == "e" and myListWord[lenOfList-4] == "m" and myListWord[lenOfList-3] == "e" and myListWord[lenOfList-2] == "n" and myListWord[lenOfList-1] == "t") :
        # EMENT -> ER
        if(checkIfWordExist(''.join(__generateWordER_5(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER_5(myListWord)), 'EMENT', 'ER', 'Nom', 'Ver')
            print(''.join(__generateWordER_5(myListWord)))

    #IF EUR
    if (myListWord[lenOfList-3] == "e" and myListWord[lenOfList-2] == "u" and myListWord[lenOfList-1] == "r") :
        # EUR -> EUSE
        if(checkIfWordExist(''.join(__generateWordEUSE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordEUSE(myListWord)), 'EUR', 'EUSE', 'Nom', 'Nom')
            print(''.join(__generateWordEUSE(myListWord)))

        # EUR -> ER
        if(checkIfWordExist(''.join(__generateWordER(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER(myListWord)), 'EUR', 'ER', 'Nom', 'Ver')
            print(''.join(__generateWordER(myListWord)))

    #IF EUSE
    if (myListWord[lenOfList-4] == "e" and myListWord[lenOfList-3] == "u" and myListWord[lenOfList-2] == "s" and myListWord[lenOfList-1] == "e") :
        # EUSE -> ER
        if(checkIfWordExist(''.join(__generateWordER_4(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER_4(myListWord)), 'EUSE', 'ER', 'Nom', 'Ver')
            print(''.join(__generateWordER_4(myListWord)))

    #IF ETTE
    if (myListWord[lenOfList-4] == "e" and myListWord[lenOfList-3] == "t" and myListWord[lenOfList-2] == "t" and myListWord[lenOfList-1] == "e" ) :
        #ETTE -> ER
        if(checkIfWordExist(''.join(__generateWordER_4(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER_4(myListWord)), 'ETTE', 'ER', 'Nom', 'Ver')
            print(''.join(__generateWordER_4(myListWord)))


    #if AL/EL
    if ((myListWord[lenOfList-2] == "e" or myListWord[lenOfList-2] == "a") and myListWord[lenOfList-1] == "l" ) :
        #EL/AL -> ALISER (Mondial -> mondialiser)
        if(checkIfWordExist(''.join(__generateWordALISER(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordALISER(myListWord)), 'AL/EL', 'ALISER', 'Nom', 'Ver')
            print(''.join(__generateWordALISER(myListWord)))

        #EL/AL -> ALISATION
        if(checkIfWordExist(''.join(__generateWordALISATION(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordALISATION(myListWord)), 'AL/EL', 'ALISATION', 'Nom', 'Nom')
            print(''.join(__generateWordALISATION(myListWord)))

        #AL -> AUX
        if(checkIfWordExist(''.join(__generateWordAUX(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordAUX(myListWord)), 'AL/EL', 'AUX', 'Nom', 'Nom:Plu')
            print(''.join(__generateWordAUX(myListWord)))

    #if ALE
    if (myListWord[lenOfList-3] == "a" and myListWord[lenOfList-2] == "l" and myListWord[lenOfList-1] == "e" ) :
        #ALE -> ALES
        if(checkIfWordExist(''.join(__generateWordALES(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordALES(myListWord)), 'AL/EL', 'ALES', 'Nom', 'Nom:Plu')
            print(''.join(__generateWordALES(myListWord)))

    #if AISON
    if (myListWord[lenOfList-5] == "a" and myListWord[lenOfList-4] == "i" and myListWord[lenOfList-3] == "s" and myListWord[lenOfList-2] == "o" and myListWord[lenOfList-1] == "n" ) :
        #AISON -> ER
        if(checkIfWordExist(''.join(__generateWordER_5(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER_5(myListWord)), 'AISON', 'ER', 'Nom', 'Ver')
            print(''.join(__generateWordER_5(myListWord)))

    #if AGE
    if (myListWord[lenOfList-3] == "a" and myListWord[lenOfList-2] == "g" and myListWord[lenOfList-1] == "e" ) :
        #AGE -> ER
        if(checkIfWordExist(''.join(__generateWordER(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER(myListWord)), 'AGE', 'ER', 'Nom', 'Ver')
            print(''.join(__generateWordER(myListWord)))

        #AGE -> IER
        if(checkIfWordExist(''.join(__generateWordIER(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordIER(myListWord)), 'AGE', 'IER', 'Nom', 'Nom')
            print(''.join(__generateWordIER(myListWord)))

    #if AIL
    if (myListWord[lenOfList-3] == "a" and myListWord[lenOfList-2] == "i" and myListWord[lenOfList-1] == "l" ) :
        #AIL -> ER
        if(checkIfWordExist(''.join(__generateWordER(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER(myListWord)), 'AIL', 'ER', 'Nom', 'Nom')
            print(''.join(__generateWordER(myListWord)))

    #if AILLE
    if (myListWord[lenOfList-5] == "a" and myListWord[lenOfList-4] == "i" and myListWord[lenOfList-3] == "l" and myListWord[lenOfList-2] == "l" and myListWord[lenOfList-1] == "e" ) :
        #AILLE -> ER
        if(checkIfWordExist(''.join(__generateWordER_5(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER(myListWord)), 'AILLE', 'IER', 'Nom', 'Nom')
            print(''.join(__generateWordER_5(myListWord)))

    #if ABLE
    if (myListWord[lenOfList-4] == "a" and myListWord[lenOfList-3] == "b" and myListWord[lenOfList-2] == "l" and myListWord[lenOfList-1] == "e" ) :
        #ABLE -> ER
        if(checkIfWordExist(''.join(__generateWordER_4(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER_4(myListWord)), 'ABLE', 'ER', 'Nom', 'Nom')
            print(''.join(__generateWordER_4(myListWord)))


    #IF IER
    if (myListWord[len(myListWord)-3] == "i" and myListWord[len(myListWord)-2] == "e" and myListWord[len(myListWord)-1] == "r") : 
        #IER -> IANT (mendier -> mendiant)
        if(checkIfWordExist(''.join(__generateWordIANT(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordIANT(myListWord)), 'IER', 'IANT', 'Nom', 'Nom')
            print(''.join(__generateWordIANT(myListWord)))

        #IER -> ER 
        if(checkIfWordExist(''.join(__generateWordER(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER(myListWord)), 'IER', 'ER', 'Nom', 'Nom')
            print(''.join(__generateWordER(myListWord)))

        #IER -> AGE
        if(checkIfWordExist(''.join(__generateWordAGE_BIS(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordAGE_BIS(myListWord)), 'IER', 'AGE', 'Nom', 'Nom')
            print(''.join(__generateWordAGE_BIS(myListWord)))

        #IER -> ERAIE (mendier -> mendiant)
        if(checkIfWordExist(''.join(__generateWordERAIE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordERAIE(myListWord)), 'IER', 'ERAIE', 'Nom', 'Nom')
            print(''.join(__generateWordERAIE(myListWord)))

    #IF EOIRE
    if (myListWord[lenOfList-5] == "o" and myListWord[lenOfList-4] == "o" and myListWord[lenOfList-3] == "i" and myListWord[lenOfList-2] == "r" and myListWord[lenOfList-1] == "e" ) :
        #EOIRE -> ER
        if(checkIfWordExist(''.join(__generateWordER_5(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER_5(myListWord)), 'EOIRE', 'ER', 'Nom', 'Nom')
            print(''.join(__generateWordER_5(myListWord)))

    #IF OIRE
    if (myListWord[lenOfList-4] == "o" and myListWord[lenOfList-3] == "i" and myListWord[lenOfList-2] == "r" and myListWord[lenOfList-1] == "e" ) :
        #OIRE -> ORIQUE
        if(checkIfWordExist(''.join(__generateWordORIQUE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordORIQUE(myListWord)), 'OIRE', 'ORIQUE', 'Nom', 'Nom')
            print(''.join(__generateWordORIQUE(myListWord)))

        #OIRE -> ER
        if(checkIfWordExist(''.join(__generateWordER_4(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER_4(myListWord)), 'OIRE', 'ER', 'Nom', 'Nom')
            print(''.join(__generateWordER_4(myListWord)))

    #IF OIR
    if (myListWord[lenOfList-3] == "o" and myListWord[lenOfList-2] == "i" and myListWord[lenOfList-1] == "r" ) :
        #OIR -> ER
        if(checkIfWordExist(''.join(__generateWordER(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER(myListWord)), 'OIR', 'ER', 'Nom', 'Nom')
            print(''.join(__generateWordER(myListWord)))

    # IF ère
    if (myListWord[lenOfList-3] == "è" and myListWord[lenOfList-2] == "r" and myListWord[lenOfList-1] == "e" ) :
        #ère -> EREAU
        if(checkIfWordExist(''.join(__generateWordEREAU(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordEREAU(myListWord)), 'ERE', 'EREAU', 'Nom', 'Nom')
            print(''.join(__generateWordEREAU(myListWord)))

    #IF IN
    if (myListWord[lenOfList-2] == "i" and myListWord[lenOfList-1] == "n" ) :
        # in -> EREAU 
        if(checkIfWordExist(''.join(__generateWordEREAU_BIS(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordEREAU_BIS(myListWord)), 'IN', 'EREAU', 'Nom', 'Nom')
            print(''.join(__generateWordEREAU_BIS(myListWord)))

    #IF RE 
    if (myListWord[lenOfList-2] == "r" and myListWord[lenOfList-1] == "e" ) :
        #RE -> USTE 
        if(checkIfWordExist(''.join(__generateWordUSTRE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordUSTRE(myListWord)), 'RE', 'USTRE', 'Nom', 'Nom')
            print(''.join(__generateWordUSTRE(myListWord)))

    #IF UCTEUR
    if (myListWord[lenOfList-6] == "u" and myListWord[lenOfList-5] == "c" and myListWord[lenOfList-4] == "t" and myListWord[lenOfList-3] == "e" and myListWord[lenOfList-2] == "u" and myListWord[lenOfList-1] == "r") :
        # UCTEUR -> UIRE
        if(checkIfWordExist(''.join(__generateWordUIRE_6(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordUIRE_6(myListWord)), 'UCTEUR', 'UIRE', 'Nom', 'Ver')
            print(''.join(__generateWordUIRE_6(myListWord)))

    #IF UCTION
    if (myListWord[lenOfList-6] == "u" and myListWord[lenOfList-5] == "c" and myListWord[lenOfList-4] == "t" and myListWord[lenOfList-3] == "i" and myListWord[lenOfList-2] == "o" and myListWord[lenOfList-1] == "n") :
        # UCTEUR -> UIRE
        if(checkIfWordExist(''.join(__generateWordUIRE_6(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordUIRE_6(myListWord)), 'UCTION', 'UIRE', 'Nom', 'Ver')
            print(''.join(__generateWordUIRE_6(myListWord)))

    #IF UCTRICE
    if (myListWord[lenOfList-7] == "u" and myListWord[lenOfList-6] == "c" and myListWord[lenOfList-5] == "t" and myListWord[lenOfList-4] == "r" and myListWord[lenOfList-3] == "i" and myListWord[lenOfList-2] == "c" and myListWord[lenOfList-1] == "e") :
        # UCTRICE -> UIRE
        if(checkIfWordExist(''.join(__generateWordUIRE_7(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordUIRE_7(myListWord)), 'UCTRICE', 'UIRE', 'Nom', 'Ver')
            print(''.join(__generateWordUIRE_7(myListWord)))


    #IF UE
    if (myListWord[lenOfList-2] == "u" and myListWord[lenOfList-1] == "e" ) :
        #UE -> IE 
        if(checkIfWordExist(''.join(__generateWordIE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordIE(myListWord)), 'UE', 'IE', 'Nom', 'Nom')
            print(''.join(__generateWordIE(myListWord)))

    #without condition, try anyway if exists
    #add ER 
    if(checkIfWordExist(''.join(__generateWordER(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordER(myListWord)), '*', 'ER', 'Nom', 'Ver')
        print(''.join(__generateWordER(myListWord)))

    #add ON 
    if(checkIfWordExist(''.join(__generateWordON_BIS(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordON_BIS(myListWord)), '*', 'ON', 'Nom', 'Nom')
        print(''.join(__generateWordON_BIS(myListWord)))

    #add ELLE 
    if(checkIfWordExist(''.join(__generateWordELLE(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordELLE(myListWord)), '*', 'ELLE', 'Nom', 'Nom')
        print(''.join(__generateWordELLE(myListWord)))

    #add ABLE #call to verb's method
    if(checkIfWordExist(''.join(__generateWordABLE(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordABLE(myListWord)), '*', 'ABLE', 'Nom', 'Adj')
        print(''.join(__generateWordABLE(myListWord)))

    #add NETTE 
    if(checkIfWordExist(''.join(__generateWordNETTE(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordNETTE(myListWord)), '*', 'NETTE', 'Nom', 'Nom')
        print(''.join(__generateWordNETTE(myListWord)))

    #add é 
    if(checkIfWordExist(''.join(__generateWordE(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordE(myListWord)), '*', 'E', 'Nom', 'Ver')
        print(''.join(__generateWordE(myListWord)))

    #add acé 
    if(checkIfWordExist(''.join(__generateWordACE(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordACE(myListWord)), '*', 'ACE', 'Nom', 'Adj')
        print(''.join(__generateWordACE(myListWord)))

    #add an
    if(checkIfWordExist(''.join(__generateWordAN(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordAN(myListWord)), '*', 'AN', 'Nom', 'Adj')
        print(''.join(__generateWordAN(myListWord)))

    #add ANE
    if(checkIfWordExist(''.join(__generateWordANE(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordANE(myListWord)), '*', 'ANE', 'Nom', 'Adj')
        print(''.join(__generateWordANE(myListWord)))

    #add al 
    if(checkIfWordExist(''.join(__generateWordAL(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordAL(myListWord)), '*', 'AL', 'Nom', 'Nom')
        print(''.join(__generateWordAL(myListWord)))

    #add GRAMME 
    if(checkIfWordExist(''.join(__generateWordGRAMME(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordGRAMME(myListWord)), '*', 'GRAMME', 'Nom', 'Nom')
        print(''.join(__generateWordGRAMME(myListWord)))
        
    #add ISME 
    if(checkIfWordExist(''.join(__generateWordISME(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordISME(myListWord)), '*', 'ISME', 'Nom', 'Nom')
        print(''.join(__generateWordISME(myListWord)))

    #add ISTE
    if(checkIfWordExist(''.join(__generateWordISTE(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordISTE(myListWord)), '*', 'ISTE', 'Nom', 'Nom')
        print(''.join(__generateWordISTE(myListWord)))

    #add IR
    if(checkIfWordExist(''.join(__generateWordIR(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordIR(myListWord)), '*', 'IR', 'Nom', 'Ver')
        print(''.join(__generateWordIR(myListWord)))

    #add AGE #call to ver's method
    if(checkIfWordExist(''.join(__generateWordAGE(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordAGE(myListWord)), '*', 'AGE', 'Nom', 'Nom')
        print(''.join(__generateWordAGE(myListWord)))

    #add ANTE #call to ver's method
    if(checkIfWordExist(''.join(__generateWordANTE(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordACE(myListWord)), '*', 'ANTE', 'Nom', 'Nom')
        print(''.join(__generateWordANTE(myListWord)))

    #add AIRE
    if(checkIfWordExist(''.join(__generateWordAIRE_BIS(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordAIRE_BIS(myListWord)), '*', 'AIRE', 'Nom', 'Nom')
        print(''.join(__generateWordAIRE_BIS(myListWord)))

    #add IAIRE
    if(checkIfWordExist(''.join(__generateWordIAIRE(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordAIRE(myListWord)), '*', 'IAIRE', 'Nom', 'Nom')
        print(''.join(__generateWordIAIRE(myListWord)))

    #add ALE
    if(checkIfWordExist(''.join(__generateWordALE(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordALE(myListWord)), '*', 'ALE', 'Nom', 'Nom')
        print(''.join(__generateWordALE(myListWord)))

    #add AL
    if(checkIfWordExist(''.join(__generateWordAL(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordAL(myListWord)), '*', 'AL', 'Nom', 'Nom')
        print(''.join(__generateWordAL(myListWord)))

    #add AIN
    if(checkIfWordExist(''.join(__generateWordAIN(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordAIN(myListWord)), '*', 'AIN', 'Nom', 'Adj')
        print(''.join(__generateWordAIN(myListWord)))

    #add AINE
    if(checkIfWordExist(''.join(__generateWordAINE(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordAINE(myListWord)), '*', 'AINE', 'Nom', 'Adj')
        print(''.join(__generateWordAINE(myListWord)))

    #add ARD
    if(checkIfWordExist(''.join(__generateWordARD(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordARD(myListWord)), '*', 'ARD', 'Nom', 'Nom')
        print(''.join(__generateWordARD(myListWord)))

    #add ARDE
    if(checkIfWordExist(''.join(__generateWordARDE(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordARDE(myListWord)), '*', 'ARDE', 'Nom', 'Nom')
        print(''.join(__generateWordARDE(myListWord)))



#METHOD GENERATE NOM

#RE -> USTE
def __generateWordUSTRE (listWord) :
    myListWordTempo = listWord.copy()
    
    #remove el
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add aliser
    myListWordTempo.append('ustre')
    return myListWordTempo

#EL/AL -> ALISER (Mondial -> mondialiser)
def __generateWordALISER (listWord) :
    myListWordTempo = listWord.copy()
    
    #remove el
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add aliser
    myListWordTempo.append('aliser')
    return myListWordTempo

#EL/AL -> ALISATION
def __generateWordALISATION (listWord) :
    myListWordTempo = listWord.copy()
    
    #remove el
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add alisation
    myListWordTempo.append('alisation')
    return myListWordTempo

#E -> ER
def __generateWordER_1 (listWord) :
    myListWordTempo = listWord.copy()
    
    #remove E
    myListWordTempo.pop()

    #add er
    myListWordTempo.append('er')
    return myListWordTempo

#EMENT -> ER
def __generateWordER_5 (listWord) :
    myListWordTempo = listWord.copy()
    
    #remove EMENT
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add er
    myListWordTempo.append('er')
    return myListWordTempo

#AL -> AUX
def __generateWordAUX(listWord) :
    myListWordTempo = listWord.copy()
    
    #remove el
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add aux
    myListWordTempo.append('aux')
    return myListWordTempo

#ALE -> ALES
def __generateWordALES(listWord) :
    myListWordTempo = listWord.copy()

    #add s
    myListWordTempo.append('s')
    return myListWordTempo

#UE -> IE
def __generateWordIE(listWord) :
    myListWordTempo = listWord.copy()
    
    #remove el
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add aux
    myListWordTempo.append('ie')
    return myListWordTempo

#e -> IE
def __generateWordIE_BIS(listWord) :
    myListWordTempo = listWord.copy()
    
    #remove e
    myListWordTempo.pop()

    #add aux
    myListWordTempo.append('ie')
    return myListWordTempo

#èRE -> EREAU
def __generateWordEREAU(listWord) :
    myListWordTempo = listWord.copy()
    #remove ère
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add ereau
    myListWordTempo.append('ereau')
    return myListWordTempo

#IN -> EREAU
def __generateWordEREAU_BIS(listWord) :
    myListWordTempo = listWord.copy()
    #remove in
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add ereau
    myListWordTempo.append('ereau')
    return myListWordTempo

#E -> ON
def __generateWordON(listWord) :
    myListWordTempo = listWord.copy()
    #remove e
    myListWordTempo.pop()

    #add on
    myListWordTempo.append('on')
    return myListWordTempo

#E -> IF
def __generateWordIF(listWord) :
    myListWordTempo = listWord.copy()
    #remove e
    myListWordTempo.pop()

    #add if
    myListWordTempo.append('if')
    return myListWordTempo

#E -> EUX
def __generateWordEUX(listWord) :
    myListWordTempo = listWord.copy()
    #remove e
    myListWordTempo.pop()

    #add eux
    myListWordTempo.append('eux')
    return myListWordTempo

#E -> AIRE
def __generateWordAIRE(listWord) :
    myListWordTempo = listWord.copy()
    #remove e
    myListWordTempo.pop()

    #add aire
    myListWordTempo.append('aire')
    return myListWordTempo

#ER -> ANCE
def __generateWordANCE(listWord) :
    myListWordTempo = listWord.copy()
    #remove er
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add ance
    myListWordTempo.append('ance')
    return myListWordTempo

#IER -> AGE
def __generateWordAGE_BIS (listWord) :
    myListWordTempo = listWord.copy()

    #REMOVE IER 
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()

    myListWordTempo.append('age')
    return myListWordTempo

#IER -> IANT
def __generateWordIANT (listWord) :
    myListWordTempo = listWord.copy()

    #REMOVE IER 
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()

    myListWordTempo.append('iant')
    return myListWordTempo

#IER -> ERAIE
def __generateWordERAIE (listWord) :
    myListWordTempo = listWord.copy()

    #REMOVE IER 
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()

    myListWordTempo.append('eraie')
    return myListWordTempo

#IER -> ER
def __generateWordER (listWord) :
    myListWordTempo = listWord.copy()

    #REMOVE IER 
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()

    myListWordTempo.append('er')
    return myListWordTempo

#AGE -> IER
def __generateWordIER (listWord) :
    myListWordTempo = listWord.copy()

    #REMOVE IER 
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()

    myListWordTempo.append('ier')
    return myListWordTempo

#EUR -> EUSE
def __generateWordEUSE(listWord) :
    myListWordTempo = listWord.copy()
    #remove EUR
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add euse
    myListWordTempo.append('euse')
    return myListWordTempo

#EUSE -> ER
def __generateWordER_4(listWord) :
    myListWordTempo = listWord.copy()
    #remove EUR
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add er
    myListWordTempo.append('er')
    return myListWordTempo

#OIRE -> ORIQUE
def __generateWordORIQUE(listWord) :
    myListWordTempo = listWord.copy()
    #remove oire
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add orique
    myListWordTempo.append('orique')
    return myListWordTempo

#UCTEUR -> UIRE
def __generateWordUIRE_6(listWord) :
    myListWordTempo = listWord.copy()
    #remove UCTEUR
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add UIRE
    myListWordTempo.append('uire')
    return myListWordTempo

#UCTRICE -> UIRE
def __generateWordUIRE_7(listWord) :
    myListWordTempo = listWord.copy()
    #remove UCTRICE
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add UIRE
    myListWordTempo.append('uire')
    return myListWordTempo

#* -> ELLE
def __generateWordELLE(listWord) :
    myListWordTempo = listWord.copy()

    #add elle
    myListWordTempo.append('elle')
    return myListWordTempo

#* -> er
def __generateWordER(listWord) :
    myListWordTempo = listWord.copy()
    
    #add ER
    myListWordTempo.append('er')
    return myListWordTempo

#* -> NETTE
def __generateWordNETTE(listWord) :
    myListWordTempo = listWord.copy()
    
    #add nette
    myListWordTempo.append('nette')
    return myListWordTempo

#* -> E
def __generateWordE(listWord) :
    myListWordTempo = listWord.copy()

    #add é
    myListWordTempo.append('é')
    return myListWordTempo

#* -> ACE
def __generateWordACE(listWord) :
    myListWordTempo = listWord.copy()

    #add acé
    myListWordTempo.append('acé')
    return myListWordTempo

#* -> ON
def __generateWordON_BIS(listWord) :
    myListWordTempo = listWord.copy()

    #add on
    myListWordTempo.append('on')
    return myListWordTempo

#* -> AL
def __generateWordAL(listWord) :
    myListWordTempo = listWord.copy()

    #add al
    myListWordTempo.append('al')
    return myListWordTempo

#* -> ANTE
def __generateWordANTE(listWord) :
    myListWordTempo = listWord.copy()

    #add ante
    myListWordTempo.append('ante')
    return myListWordTempo

#* -> ISME
def __generateWordISME(listWord) :
    myListWordTempo = listWord.copy()

    #add ISME
    myListWordTempo.append('isme')
    return myListWordTempo

#* -> ISTE
def __generateWordISTE(listWord) :
    myListWordTempo = listWord.copy()

    #add ISTE
    myListWordTempo.append('iste')
    return myListWordTempo

#* -> IR
def __generateWordIR(listWord) :
    myListWordTempo = listWord.copy()

    #add IR
    myListWordTempo.append('ir')
    return myListWordTempo

#* -> GRAMME
def __generateWordGRAMME(listWord) :
    myListWordTempo = listWord.copy()

    #add gramme
    myListWordTempo.append('gramme')
    return myListWordTempo

#* -> AIRE
def __generateWordAIRE_BIS(listWord) :
    myListWordTempo = listWord.copy()

    #add AIRE
    myListWordTempo.append('aire')
    return myListWordTempo

#* -> IAIRE
def __generateWordIAIRE(listWord) :
    myListWordTempo = listWord.copy()

    #add IAIRE
    myListWordTempo.append('iaire')
    return myListWordTempo

#* -> ALE
def __generateWordALE(listWord) :
    myListWordTempo = listWord.copy()

    #add ALE
    myListWordTempo.append('ale')
    return myListWordTempo

#* -> AN
def __generateWordAN(listWord) :
    myListWordTempo = listWord.copy()

    #add AN
    myListWordTempo.append('an')
    return myListWordTempo

#* -> ANE
def __generateWordANE(listWord) :
    myListWordTempo = listWord.copy()

    #add ANE
    myListWordTempo.append('ane')
    return myListWordTempo

#* -> ALE
def __generateWordALE(listWord) :
    myListWordTempo = listWord.copy()

    #add ALE
    myListWordTempo.append('ale')
    return myListWordTempo

#* -> AL
def __generateWordAL(listWord) :
    myListWordTempo = listWord.copy()

    #add AL
    myListWordTempo.append('al')
    return myListWordTempo

#* -> AIRE
def __generateWordAIRE_BIS(listWord) :
    myListWordTempo = listWord.copy()

    #add AIRE
    myListWordTempo.append('aire')
    return myListWordTempo

#* -> AIN
def __generateWordAIN(listWord) :
    myListWordTempo = listWord.copy()

    #add AIN
    myListWordTempo.append('ain')
    return myListWordTempo

#* -> AINE
def __generateWordAINE(listWord) :
    myListWordTempo = listWord.copy()

    #add AINE
    myListWordTempo.append('aine')
    return myListWordTempo

#* -> ARD
def __generateWordARD(listWord) :
    myListWordTempo = listWord.copy()

    #add ARD
    myListWordTempo.append('ard')
    return myListWordTempo

#* -> ARDE
def __generateWordARDE(listWord) :
    myListWordTempo = listWord.copy()

    #add ARDE
    myListWordTempo.append('arde')
    return myListWordTempo

###########################
###########################
###########################

#Generate new word from type : ADJ
def transformADJ(word) :
    #transform to list character
    myListWord = list(word)
    lenOfList = len(myListWord)

    #IF E
    if (myListWord[lenOfList-1] == "e" ) :
        #E -> ISER (econome -> economiser)
        if(checkIfWordExist(''.join(__generateWordISER(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordISER(myListWord)), 'E', 'ISER', 'Adj', 'Ver')
            print(''.join(__generateWordISER(myListWord)))

        #E -> ITE 
        if(checkIfWordExist(''.join(__generateWordITE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordITE(myListWord)), 'E', 'ITE', 'Adj', 'Adj')
            print(''.join(__generateWordITE(myListWord)))

        #E -> ESSE 
        if(checkIfWordExist(''.join(__generateWordESSE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordESSE(myListWord)), 'E', 'ESSE', 'Adj', 'Nom')
            print(''.join(__generateWordESSE(myListWord)))

        #E -> IE 
        if(checkIfWordExist(''.join(__generateWordIE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordIE(myListWord)), 'E', 'IE', 'Adj', 'Nom')
            print(''.join(__generateWordIE(myListWord)))

    #IF TIF 
    if (myListWord[lenOfList-3] == "t" and myListWord[lenOfList-2] == "i" and myListWord[lenOfList-1] == "f" ) :
        #TIF -> TIVITE
        if(checkIfWordExist(''.join(__generateWordTIVITE(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordESSE(myListWord)), 'E', 'TIVITE', 'Adj', 'Nom')
            print(''.join(__generateWordTIVITE(myListWord)))

    #IF ENT/ANT
    if ((myListWord[lenOfList-3] == "e" or myListWord[lenOfList-3] == "a") and myListWord[lenOfList-2] == "n" and myListWord[lenOfList-1] == "t" ) :
        #ENT/ANT -> MMENT
        if(checkIfWordExist(''.join(__generateWordMMENT(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordESSE(myListWord)), 'ENT/ANT', 'MMENT', 'Adj', 'Adv')
            print(''.join(__generateWordMMENT(myListWord)))

        #ENT/ANT -> ER
        if(checkIfWordExist(''.join(__generateWordER(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER(myListWord)), 'ENT/ANT', 'ER', 'Adj', 'Ver')
            print(''.join(__generateWordER(myListWord)))


    #IF ENTE/ANTE
    if ((myListWord[lenOfList-4] == "e" or myListWord[lenOfList-4] == "a") and myListWord[lenOfList-3] == "n" and myListWord[lenOfList-2] == "t" and myListWord[lenOfList-1] == "e" ) :
        #ENTE/ANTE -> MMENT
        if(checkIfWordExist(''.join(__generateWordMMENT_BIS(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordMMENT_BIS(myListWord)), 'ENTE/ANTE', 'MMENT', 'Adj', 'Adv')
            print(''.join(__generateWordMMENT_BIS(myListWord)))

    #IF IBLE
    if (myListWord[lenOfList-4] == "i" and myListWord[lenOfList-3] == "b" and myListWord[lenOfList-2] == "l" and myListWord[lenOfList-1] == "e" ) :
        #IBLE -> ER
        if(checkIfWordExist(''.join(__generateWordER_4(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordER_4(myListWord)), 'IBLE', 'ER', 'Adj', 'Ver')
            print(''.join(__generateWordER_4(myListWord)))

    #Try without condition
    #* -> EMENT (rapide -> rapidement)
    if(checkIfWordExist(''.join(__generateWordMENT(myListWord)))) :
        __writeToRuleFile(word, ''.join(__generateWordESSE(myListWord)), '*', 'EMENT', 'Adj', 'Nom')
        print(''.join(__generateWordMENT(myListWord)))

#METHOD GENERATE ADJ
#E -> EMENT
def __generateWordMENT(listWord) :
    myListWordTempo = listWord.copy()

    #add ment
    myListWordTempo.append('ment')
    return myListWordTempo

#E -> ITE
def __generateWordITE(listWord) :
    myListWordTempo = listWord.copy()
    #remove e
    myListWordTempo.pop()

    #add on
    myListWordTempo.append('ité')
    return myListWordTempo

#E -> ESSE
def __generateWordESSE(listWord) :
    myListWordTempo = listWord.copy()
    #remove e
    myListWordTempo.pop()

    #add aire
    myListWordTempo.append('esse')
    return myListWordTempo

#ENTE/ANTE -> EMMENT
def __generateWordMMENT_BIS(listWord) :
    myListWordTempo = listWord.copy()
    #remove NTE
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add MMENT
    myListWordTempo.append('mment')
    return myListWordTempo

#ENT/ANT -> EMMENT
def __generateWordMMENT(listWord) :
    myListWordTempo = listWord.copy()
    #remove NT
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add MMENT
    myListWordTempo.append('mment')
    return myListWordTempo

#E -> ISER
def __generateWordISER(listWord) :
    myListWordTempo = listWord.copy()
    #remove e
    myListWordTempo.pop()

    #add ISER
    myListWordTempo.append('iser')
    return myListWordTempo

#IF -> IVITE
def __generateWordTIVITE(listWord) :
    myListWordTempo = listWord.copy()
    #remove if
    myListWordTempo.pop()
    myListWordTempo.pop()

    #add ISER
    myListWordTempo.append('ivité')
    return myListWordTempo

###########################
###########################
###########################

def transformADV(word) :
    #transform to list character
    myListWord = list(word)
    lenOfList = len(myListWord)

    #IF ANT 
    if (myListWord[lenOfList-3] == "a" and myListWord[lenOfList-2] == "n" and myListWord[lenOfList-1] == "t" ) :
        #ANT -> ANCE
        if(checkIfWordExist(''.join(__generateWordANCE_BIS(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordESSE(myListWord)), 'ANT', 'ANCE', 'Adv', 'Nom')
            print(''.join(__generateWordANCE_BIS(myListWord)))

    if (myListWord[lenOfList-4] == "a" and myListWord[lenOfList-3] == "n" and myListWord[lenOfList-2] == "t" and myListWord[lenOfList-1] == "e") :
        #ANTE -> ANCE
        if(checkIfWordExist(''.join(__generateWordANCE_BIS(myListWord)))) :
            __writeToRuleFile(word, ''.join(__generateWordESSE(myListWord)), 'ANTE', 'ANCE', 'Adj', 'Nom')
            print(''.join(__generateWordANCE_BIS(myListWord)))

#METHOD GENERATE VERB ADV


#ANT -> ANCE
def __generateWordANCE_BIS(listWord) :
    myListWordTempo = listWord.copy()
    if (myListWordTempo[len(myListWordTempo)-1] == "e") : 
        myListWordTempo.pop()

    #remove ANT
    myListWordTempo.pop()
    myListWordTempo.pop()
    myListWordTempo.pop()


    #add ANCE
    myListWordTempo.append('ance')
    return myListWordTempo
    
##############
##############
#main()
##############
##########v###
def __generateWord(word) :
    myListWord = list(word)
    lenOfList = len(myListWord)

    if(isVer_Inf(word)):
        transformVer_Inf(word)

    if(isNom(word)) :
        transformNom(word)

    if(isADJ(word)) :
        transformADJ(word)

    if(isADV(word)) :
        transformADV(word)

__generateWord(word)


#close file
file_r_pos.close()
file_rule.close()