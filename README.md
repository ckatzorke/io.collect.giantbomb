# collectio.giantbomb

Basic skeleton for a Java based API wrapper for the giantbomb rest api. You pretty fast reach the boundaries of flexibility due to the strictly typed system in combination with type erasure when using Generics...
Basic idea is to provide a GiantBombTemplate in a Spring convenient way, with a dedicated set of methods. Along the standard RestTemplate you will need an additional io.collect.giantbomb.config.GiantBombProperties config, containing your api key for GiantBomb

## usage
   see bootapp