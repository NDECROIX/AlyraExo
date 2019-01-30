#!/usr/bin/env python3 
# -*- coding: utf-8 -*-
import itertools

satoshis = [13000, 9000, 2000, 1500, 3500, 2800, 5000, 1500] 
octets =   [2000,  6000, 800,  700,  1200, 1000, 1300, 600]  
octetsMax = 6000
octetsUtulise = octetsMax
n = len(satoshis) - 1 

def meilleurPBR( satoshis , octets , octetsM , n):
    """
        fonction qui retourne le maximum de satoshis
        en fonction du nombre d'octets respecter
    """
  
    if n < 0 or octetsM == 0 : 
        return 0
        
    if (octets[n] > octetsM): # si le bloc d'octets est suppérieur au max autorisé on passe au suivant 
	
        return meilleurPBR( satoshis, octets ,  octetsM, n-1) 
  
    else: # retourne la valeur maximum
        global octetsUtulise
        octetsUtulise = octetsMax - octetsM
        
        return max(meilleurPBR( satoshis, octets , octetsM - octets[n]  , n-1) + satoshis[n], 
                   meilleurPBR( satoshis, octets , octetsM , n-1)) 
    
    

def transactionRentable(satoshisTT, octetsUtulise):
    """
        fonction qui retrouve les transaction les plus rentable 
        en fonction du total de satoshis et du nombre d'octets octetsUtulise
    """
    listeSatoshis = satoshis 
    listeOctets = octets
    
    for i in range(1, len(listeSatoshis)): 
        
        for sato in itertools.combinations(listeSatoshis, i): 
            
            if sum(sato) == satoshisTT: # pour toutes les sommes de combinaisons de satoshis = à satoshisTT
                
                for octe in itertools.combinations(listeOctets, i):
                    
                    if sum(octe) == octetsUtulise: # et pour toutes les combinaison d'octets = à octetsUtulise
                        return(octe) # retourne la combinaison d'octets 
         
octetsUtil = octetsUtulise

print ("Le pourboir maximum est atteind avec les trasactions ",
        transactionRentable(meilleurPBR( satoshis, octets , octetsMax , n), octetsUtil ),
        "pour un total de  :",
        meilleurPBR( satoshis, octets , octetsMax , n), "satoshis" )

print ("On a une complexité de O(2^n)")
    
    





