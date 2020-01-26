#ifndef FASTDICTIONARY_HPP
#define FASTDICTIONARY_HPP

#include <functional>

#include "AbstractDictionary.hpp"

template<typename T>
class FastDictionary: public AbstractDictionary<T> {

	public:

		FastDictionary(const unsigned int reservedSize);

		unsigned int size() const;

	protected:

		unsigned int newIndex(const std::string &key);
		int findIndex(const std::string &key) const;

		unsigned int getHash(const std::string &key) const;
		void grow();
};



//On pose "" comme une clé nulle, aucun élément n'a été attribué à cette case



template<typename T>
FastDictionary<T>::FastDictionary(const unsigned int reservedSize) {

	AbstractDictionary<T>::m_dictionary.reserve(reservedSize);

	for(unsigned int i{0}; i < reservedSize; i++) { AbstractDictionary<T>::m_dictionary.emplace_back(std::make_pair("", T{})); }
}

template<typename T>
unsigned int FastDictionary<T>::size() const { return AbstractDictionary<T>::m_nbElements; }

template<typename T>
void FastDictionary<T>::grow() {

	if(static_cast<float>(AbstractDictionary<T>::m_nbElements) >= 3.f*static_cast<float>(AbstractDictionary<T>::m_dictionary.size())/4.f) {

		//Must grow

		//Sauvergarde les anciennes valeurs
		std::vector<std::pair<std::string, T>> oldDico;

		for(std::pair<std::string, T> currentPair: AbstractDictionary<T>::m_dictionary) {

			if(currentPair.first != "") { oldDico.emplace_back(currentPair); }
		}

		//Efface et agrandi

		unsigned int newSize{static_cast<unsigned int>(AbstractDictionary<T>::m_dictionary.size())*2};
		AbstractDictionary<T>::m_dictionary.clear();
		AbstractDictionary<T>::m_nbElements = 0; // Ajoute 1 à chaque newIndex, on remet donc à 0
		for(unsigned int i{0}; i < newSize; i++) { AbstractDictionary<T>::m_dictionary.emplace_back(std::make_pair("", T{})); }

		//Réécrit les anciennes valeurs

		for(std::pair<std::string, T> currentPair: oldDico) { AbstractDictionary<T>::m_dictionary[newIndex(currentPair.first)] = currentPair; }
	}
}

template<typename T>
unsigned int FastDictionary<T>::newIndex(const std::string &key) { 

	//Check si on à besoin de grandir, et si oui le fait
	grow();

	unsigned int index{getHash(key)%static_cast<unsigned int>(AbstractDictionary<T>::m_dictionary.size())};

	while(AbstractDictionary<T>::m_dictionary[index].first != "") { 

		index++; 
		if(index >= AbstractDictionary<T>::m_dictionary.size()) { index = 0; }
	}


	AbstractDictionary<T>::m_nbElements++;
	return index;
}




template<typename T>
int FastDictionary<T>::findIndex(const std::string &key) const {

	unsigned int hashValue{getHash(key)%static_cast<unsigned int>(AbstractDictionary<T>::m_dictionary.size())};
	unsigned int currentValue{hashValue};

	while(AbstractDictionary<T>::m_dictionary[currentValue].first != key) { 

		currentValue++;
		if(currentValue >= AbstractDictionary<T>::m_dictionary.size()) { currentValue = 0; }

		//On a parcouru tout le tableau, mais on n'a pas trouvé.
		if(currentValue == hashValue) { return -1; }
	}

	return currentValue;
}



template<typename T>
unsigned int FastDictionary<T>::getHash(const std::string &key) const { return std::hash<std::string>()(key); }

#endif