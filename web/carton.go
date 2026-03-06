package web

import (
	"github.com/TypingHare/burrow/v2026/kernel"
	"github.com/TypingHare/web.carton/v2026/web/web"
	webShare "github.com/TypingHare/web.carton/v2026/web/web/share"
)

const CartonName = "github.com/TypingHare/web.carton"

func RegisterCartonTo(warehouse *kernel.Warehouse) error {
	carton := kernel.NewCarton()
	carton.Metadata.Set(kernel.MetadataName, "github.com/TypingHare/web.carton")
	carton.Metadata.Set(kernel.MetadataVersion, "2026.0.0")
	carton.Metadata.Set(kernel.MetadataAuthor, "James Chen")
	carton.Metadata.Set(kernel.MetadataEmail, "jameschan312.cn@gmail.com")

	kernel.AddTypedDecorationFactory(
		carton,
		"web",
		webShare.ParseWebSpec,
		web.BuildWebDecoration,
	)
	warehouse.RegisterCarton(carton)

	return nil
}
