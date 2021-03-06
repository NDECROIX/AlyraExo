pragma solidity ^0.5.2;



contract CanalDePaiment {
    
    enum EtatCanal {
        VIDE,
        ACTIF,
        ENCOURSFERMETURE,
        FERME
    }
    
    address partieA;
    address partieB;
    uint montant;
    EtatCanal etat;
    uint blocFermeture; 
    uint dernierNonce;
    uint equilibreA;
    uint equilibreB;
    
    constructor(uint _montant, address _partieA, address _partieB) public {
        
        montant = _montant;
        partieA = _partieA;
        partieB = _partieB;
        
    }
    
    modifier onlyMontant() {
	
        uint reste = montant - (equilibreA + equilibreB);
		_;
        if (msg.value > reste){
            msg.sender.transfer(msg.value - reste);
        }
    }
    
    modifier onlyParties() {
        require(msg.sender == partieA || msg.sender == partieB);
        _;
    }
	
	modifier etatCanal(EtatCanal _etat) {
        require(etat == _etat);
        _;
    }
    
    function financer() public payable onlyMontant() etatCanal(EtatCanal.VIDE) onlyParties(){
        
        if (msg.sender == partieA) equilibreA += msg.value;
        else if (msg.sender == partieB) equilibreB += msg.value;
        
        if(equilibreA + equilibreB >= montant) etat = EtatCanal.ACTIF;
        
    }
    
    function message(uint _dernierNonce, uint _equilibreA, uint _equilibreB) public pure returns (bytes32) {
        
        return keccak256(abi.encodePacked(_dernierNonce, _equilibreA, _equilibreB));
        
    }
    
    function fermeture(uint _nonce, uint _equilibreA, uint _equilibreB, bytes memory _signature) public onlyParties(){
        require(etat == EtatCanal.ACTIF || etat == EtatCanal.ENCOURSFERMETURE);
        require(_nonce > dernierNonce);
        require(ECDSA.recover(
                ECDSA.toEthSignedMessageHash(
                message(_nonce, _equilibreA, _equilibreB)), _signature) == partieOpposee(msg.sender));
       
        dernierNonce = _nonce;
        equilibreA = _equilibreA;
        equilibreB = _equilibreB;
        blocFermeture = block.number + 23; // temps minimum pour contester la preuve d'équilibre avant fermeture du canal 
        if (etat == EtatCanal.ACTIF) etat = EtatCanal.ENCOURSFERMETURE;
        
    } 
    
    function partieOpposee(address partie) private view returns(address) {
      
      if (partie == partieA) return partieB;
      else return partieA;
      
    }
}

/**
 * @title Elliptic curve signature operations
 * @dev Based on https://gist.github.com/axic/5b33912c6f61ae6fd96d6c4a47afde6d
 * TODO Remove this library once solidity supports passing a signature to ecrecover.
 * See https://github.com/ethereum/solidity/issues/864
 */

library ECDSA {
    /**
     * @dev Recover signer address from a message by using their signature
     * @param hash bytes32 message, the hash is the signed message. What is recovered is the signer address.
     * @param signature bytes signature, the signature is generated using web3.eth.sign()
     */
    function recover(bytes32 hash, bytes memory signature) internal pure returns (address) {
        // Check the signature length
        if (signature.length != 65) {
            return (address(0));
        }

        // Divide the signature in r, s and v variables
        bytes32 r;
        bytes32 s;
        uint8 v;

        // ecrecover takes the signature parameters, and the only way to get them
        // currently is to use assembly.
        // solhint-disable-next-line no-inline-assembly
        assembly {
            r := mload(add(signature, 0x20))
            s := mload(add(signature, 0x40))
            v := byte(0, mload(add(signature, 0x60)))
        }

        // EIP-2 still allows signature malleability for ecrecover(). Remove this possibility and make the signature
        // unique. Appendix F in the Ethereum Yellow paper (https://ethereum.github.io/yellowpaper/paper.pdf), defines
        // the valid range for s in (281): 0 < s < secp256k1n ÷ 2 + 1, and for v in (282): v ∈ {27, 28}. Most
        // signatures from current libraries generate a unique signature with an s-value in the lower half order.
        //
        // If your library generates malleable signatures, such as s-values in the upper range, calculate a new s-value
        // with 0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141 - s1 and flip v from 27 to 28 or
        // vice versa. If your library also generates signatures with 0/1 for v instead 27/28, add 27 to v to accept
        // these malleable signatures as well.
        if (uint256(s) > 0x7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF5D576E7357A4501DDFE92F46681B20A0) {
            return address(0);
        }

        if (v != 27 && v != 28) {
            return address(0);
        }

        // If the signature is valid (and not malleable), return the signer address
        return ecrecover(hash, v, r, s);
    }

    /**
     * toEthSignedMessageHash
     * @dev prefix a bytes32 value with "\x19Ethereum Signed Message:"
     * and hash the result
     */
    function toEthSignedMessageHash(bytes32 hash) internal pure returns (bytes32) {
        // 32 is the length in bytes of hash,
        // enforced by the type signature above
        return keccak256(abi.encodePacked("\x19Ethereum Signed Message:\n32", hash));
    }
}

