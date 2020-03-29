import os,sys

word = input("Veuillez saisir une verbe : " )

def __generateWord(word) :
    myListWord = list(word)
    lenOfList = len(myListWord)
    if (myListWord[lenOfList-2] == "e" and myListWord[lenOfList-1] == "r" ) :
        myListWord.pop()
        myListWord.pop()
        print(''.join(__generateWordAGE(myListWord)))
        print(''.join(__generateWordEMENT(myListWord)))
        print(''.join(__generateWordEUR(myListWord)))
        print(''.join(__generateWordEUSE(myListWord)))
        if(myListWord[len(myListWord)-1] == 'g') :
            print(''.join(__generateWordGER(myListWord)))
        print(''.join(__generateWordOIR(myListWord)))
        print(''.join(__generateWordABLE(myListWord)))
        print(''.join(__generateWordE(myListWord)))
        print(''.join(__generateWordANT(myListWord)))
        print(''.join(__generateWordETTE(myListWord)))
        if(myListWord[len(myListWord)-2] == "q" and myListWord[len(myListWord)-1] == "u") :
            print(''.join(__generateWordATION(myListWord)))

    elif (myListWord[lenOfList-2] == "i" and myListWord[lenOfList-1] == "r") : 
        myListWord.pop()

    else :
        print("Le mot saisi n'est pas reconnu par le programme")

#------------ ER ------------
def __generateWordAGE (listWord):
    myListWordTempo = listWord.copy()
    myListWordTempo.append('age')
    return myListWordTempo

def __generateWordEMENT (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ement')
    return myListWordTempo

def __generateWordEUR (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('eur')
    return myListWordTempo

def __generateWordEUSE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('euse')
    return myListWordTempo

def __generateWordGER (listWord) :
    lenOfListWord = len(listWord)
    if(listWord[lenOfListWord-1] == 'g') :
        myListWordTempo = listWord.copy()
        myListWordTempo.append('eoire')
        return myListWordTempo

def __generateWordOIR (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('oir')
    return myListWordTempo

def __generateWordETTE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ette')
    return myListWordTempo

def __generateWordANT (listWord) :
    myListWordTempo = listWord.copy()
    if(myListWordTempo[len(myListWordTempo)-2] == "q" and myListWordTempo[len(myListWordTempo)-1] == "u") :
        myListWordTempo.pop()
        myListWordTempo.pop()
        myListWordTempo.append("cant")
    else : 
        myListWordTempo.append('ant')
    return myListWordTempo

def __generateWordABLE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('able')
    return myListWordTempo
        
def __generateWordE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('e')
    return myListWordTempo

def __generateWordATION (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.pop()
    myListWordTempo.append('cation')
    return myListWordTempo

#------------ IR ------------
def __generateWordEMENT (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ssement')
    return myListWordTempo

def __generateWordEUR (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('sseur')
    return myListWordTempo

def __generateWordEUSE (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('sseuse')
    return myListWordTempo


def __generateWordANT (listWord) :
    myListWordTempo = listWord.copy()
    myListWordTempo.append('ssant')
    return myListWordTempo


__generateWord(word)