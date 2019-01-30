#!/usr/bin/env python3 
# -*- coding: utf-8 -*-

def meilleurPBR(octetsMax, octets, satoshis, n): 
    """
        fonction qui retourne le maximum de satoshis pour un nombre Max d'octets
        en fonction des disponibilités dans la liste passé en paramètre
    """
    
    K = [[0 for x in range(octetsMax+1)] for x in range(n+1)] 
    
    for i in range(n+1): 
        
        for w in range(octetsMax+1): 
            
            if i==0 or w==0: 
                K[i][w] = 0
                
            elif octets[i-1] <= w: 
                K[i][w] = max(satoshis[i-1] + K[i-1][w-octets[i-1]],  K[i-1][w])
                
            else: 
                K[i][w] = K[i-1][w] 
                
                
    return K[n][octetsMax] 
  
satoshis = [13000, 9000, 2000, 1500, 3500, 2800, 5000, 1500]
octets = [2000, 6000, 800, 700, 1200, 1000, 1300, 600]
octetsMax = 6000
n = len(satoshis) 

print("Le nombre de satoshis maximum atteignable est de ",meilleurPBR(octetsMax, octets, satoshis, n)) 

print ("On a une complexité de O(n*octetsMax)")