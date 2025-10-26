package cc.worldmandia.webshoppersonal.db

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.HMAC
import dev.whyoleg.cryptography.algorithms.SHA384
import dev.whyoleg.cryptography.algorithms.SHA512
import dev.whyoleg.cryptography.materials.key.KeyDecoder
import dev.whyoleg.cryptography.materials.key.KeyGenerator
import dev.whyoleg.cryptography.operations.Hasher
import kotlin.io.encoding.Base64

// TODO
//class EncryptedWebLocalStorageImpl(
//    private val provider: CryptographyProvider = CryptographyProvider.Default,
//    private val keyGenerator: KeyGenerator<HMAC.Key> = provider.get(HMAC).keyGenerator(SHA512),
//    private val decoder: KeyDecoder<HMAC.Key.Format, HMAC.Key> = provider.get(HMAC).keyDecoder(SHA512)
//): WebLocalStorageImpl() {
//
//    val encoder: HMAC.Key = keyGenerator.generateKeyBlocking()
//
//    override fun get(key: String): String? {
//         decoder.decodeFromByteArrayBlocking(HMAC.Key.Format.RAW, key.encodeToByteArray())
//    }
//
//    override fun put(key: String, value: String) {
//        super.put(Base64.encode(key.encodeToByteArray()), Base64.encode(value.encodeToByteArray()))
//    }
//
//    override fun remove(key: String) {
//        super.remove(Base64.encode(key.encodeToByteArray()))
//    }
//
//    override fun clear(key: String) {
//        super.clear(Base64.encode(key.encodeToByteArray()))
//    }
//}