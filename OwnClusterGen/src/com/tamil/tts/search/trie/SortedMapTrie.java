
package com.tamil.tts.search.trie;


import com.tamil.tts.search.base.TrieNode;
import com.tamil.tts.search.node.TreeMapNode;

public class SortedMapTrie<V> extends MapTrie<V> {

    @Override
    protected TrieNode<V> onCreateRootNode() {
        return new TreeMapNode<>(ROOT_KEY);
    }
}
