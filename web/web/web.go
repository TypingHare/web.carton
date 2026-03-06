package web

import (
	"fmt"

	"github.com/TypingHare/burrow/v2026/burrow/core"
	"github.com/TypingHare/burrow/v2026/burrow/larder"
	larderShare "github.com/TypingHare/burrow/v2026/burrow/larder/share"
	"github.com/TypingHare/burrow/v2026/burrow/redirector"
	"github.com/TypingHare/burrow/v2026/kernel"
	"github.com/TypingHare/web.carton/v2026/web/web/command"
	"github.com/TypingHare/web.carton/v2026/web/web/share"
)

type WebDecoration struct {
	kernel.Decoration[share.WebSpec]
}

func (d *WebDecoration) RawSpec() kernel.RawSpec {
	return kernel.RawSpec{
		"autoRedirect":     d.Spec().AutoRedirect,
		"silentlyRedirect": d.Spec().SilentlyRedirect,
	}
}

func (d *WebDecoration) Dependencies() []string {
	return []string{
		kernel.GetDecorationID("larder", kernel.CartonName),
		kernel.GetDecorationID("redirector", kernel.CartonName),
	}
}

func (d *WebDecoration) Assemble() error {
	coreDecoration, err := core.UseDecoration(d)
	if err != nil {
		return fmt.Errorf("failed to get core decoration: %w", err)
	}
	coreDecoration.SetCommand(nil,
		command.ListCommand(d),
		command.AddCommand(d),
	)

	larderDecoration, err := kernel.Use[*larder.LarderDecoration](d.Chamber())
	if err != nil {
		return fmt.Errorf("failed to get larder decoration: %w", err)
	}

	_, err = larderShare.AddCabinet(
		larderDecoration,
		share.WebCabinetName,
		share.SerializeWebRecord,
		share.DeserializeWebRecord,
	)
	if err != nil {
		return fmt.Errorf("failed to add cabinet: %w", err)
	}

	redirectorDecoration, err := redirector.UseDecoration(d)
	if err != nil {
		return fmt.Errorf("failed to get redirector decoration: %w", err)
	}

	if d.Spec().AutoRedirect {
		handler := share.GetWebRedirectionHandler(d, d.Spec().SilentlyRedirect)
		redirectorDecoration.RedirectionHandler = handler
	}

	return nil
}

func (d *WebDecoration) Launch() error {
	cabinet, err := share.GetWebCabinet(d)
	if err != nil {
		return fmt.Errorf("failed to get web cabinet: %w", err)
	}
	cabinet.Load()

	return nil
}

func (d *WebDecoration) Terminate() error {
	cabinet, err := share.GetWebCabinet(d)
	if err != nil {
		return fmt.Errorf("failed to get web cabinet: %w", err)
	}
	cabinet.Save()

	return nil
}
func (d *WebDecoration) Disassemble() error { return nil }

func BuildWebDecoration(
	chamber *kernel.Chamber,
	spec share.WebSpec,
) (kernel.DecorationInstance, error) {
	return &WebDecoration{
		Decoration: *kernel.NewDecoration(chamber, spec),
	}, nil
}
